package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.ChemicalCompoundsMapper;
import com.lplb.modular.model.entity.ChemicalCompounds;
import com.lplb.modular.model.export.ChemicalCompoundsExport;
import com.lplb.modular.model.query.ChemicalCompoundsQuery;
import com.lplb.modular.model.vo.TherapyStatisticsVo;
import com.lplb.modular.service.ChemicalCompoundsService;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表服务实现类
 */
@Service
@Transactional
public class ChemicalCompoundsServiceImpl extends ServiceImpl<ChemicalCompoundsMapper, ChemicalCompounds> implements ChemicalCompoundsService {

    private final String tableName = "in_chemical_compounds";
    private final String filePath = "/09.Therapy/Chemical compounds";
    private final String fileName = "Chemical_compounds";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private DiseaseService diseaseService;

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    @Override
    public Boolean importExcel(MultipartFile file) {

        InputStream inputStream = null;
        try {
            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 设置表头
            reader = this.readHeaderAlias(reader);

            // 获取列表
            List<ChemicalCompounds> list = reader.readAll(ChemicalCompounds.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        flag.set(save(item));

                        errorLine.set(list.indexOf(item));
                        if (!flag.get()) {
                            throw new ServiceException(500, "数据导入失败，失败行数：" + errorLine.get());
                        }
                    }
                });
            });
            return flag.get();
        } catch (IOException e) {
            throw new ServiceException(500, "输入读取异常");
        } finally {
            if (ObjectUtils.isNotEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ServiceException(500, "输入流关闭异常");
                }
            }
        }
    }

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<ChemicalCompounds> pageList(ChemicalCompoundsQuery query) {
        // 分页
        Page<ChemicalCompounds> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<ChemicalCompounds> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(ChemicalCompounds::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(ChemicalCompounds::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(ChemicalCompounds::getSpecies, query.getSpecies());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum = StringUtils.camelToUnderline(order[0]);

            if (OrderByEnums.ASC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " ASC");
            } else if (OrderByEnums.DESC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " DESC");
            }
        }
        page = this.page(page, wrapper);
        return page;
    }

    /**
     * 根据基因名称查询西药列表
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public List<ChemicalCompounds> listByGene(String geneName, String disease) {
        LambdaQueryWrapper<ChemicalCompounds> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ChemicalCompounds::getDiseaseRelatedGenes, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.like(ChemicalCompounds::getDisease, disease);
        }
        List<ChemicalCompounds> result = this.list(wrapper);
        return result;
    }

    /**
     * Chemical Compounds数据统计
     *
     * @return
     */
    @Override
    public TherapyStatisticsVo chemicalCompoundsStatistics(TherapyStatisticsVo therapyStatistic) {
        Map<String, Integer> map = new HashMap<>();

        List<ChemicalCompounds> all = this.list();
        all.forEach(item -> {
            // Inference Network
            List<String> keys = new ArrayList<>(Arrays.asList(item.getInferenceNetwork().split("\\|")));
            // 去空格
            keys.stream().map(i -> i.trim()).collect(Collectors.toList());
            // 去空
            keys.stream().filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());

            // 循环
            keys.forEach(key -> {
                Integer value;
                if (map.containsKey(key)) {
                    value = map.get(key) + 1;
                } else {
                    value = 1;
                }
                map.put(key, value);
            });
        });

        List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }

        });

        Map<String, Integer> allMap = new LinkedHashMap<>();
        list.forEach(item -> {
            allMap.put(item.getKey(), item.getValue());
        });

        // 遍历且只取前十
        Map<String, Integer> top10ChemicalCompoundsAssociatedWithIntestinalDiseases = new LinkedHashMap<>();
        Iterator<Map.Entry<String, Integer>> iterator = allMap.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext() && i < 10) {
            Map.Entry<String, Integer> next = iterator.next();
            top10ChemicalCompoundsAssociatedWithIntestinalDiseases.put(next.getKey(), next.getValue());
            i++;
        }
        therapyStatistic.setTop10ChemicalCompoundsAssociatedWithIntestinalDiseases(top10ChemicalCompoundsAssociatedWithIntestinalDiseases);

        // 遍历全部
//        Map<String, Integer> map1 = new LinkedHashMap<>();
//        // 总数量
//        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
//        allMap.forEach((key, value) -> {
//            totalCount.updateAndGet(v -> v + value);
//        });
//        // 1%数量
//        Double percent = totalCount.get() * 0.01;

        Map<String, Integer> chemicalCompounds = new LinkedHashMap<>();
        allMap.forEach((key, value) -> {
            if (value == 1) {
                int other;
                if (chemicalCompounds.containsKey("other")) {
                    other = chemicalCompounds.get("other") + value;
                } else {
                    other = value;
                }
                chemicalCompounds.put("other", other);
            } else {
                chemicalCompounds.put(key, value);
            }
        });
        therapyStatistic.setChemicalCompounds(chemicalCompounds);
        return therapyStatistic;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, ChemicalCompoundsQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<ChemicalCompounds> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(ChemicalCompounds::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(ChemicalCompounds::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(ChemicalCompounds::getSpecies, query.getSpecies());
        }

        // 查询数据集合
        List<ChemicalCompounds> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<ChemicalCompounds> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ChemicalCompoundsExport> exports = BeanConvertUtil.listConvert(list, ChemicalCompoundsExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Home_Therapy_Chemical Compounds_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ChemicalCompoundsExport.class)
                    .excelType(ExcelTypeEnum.CSV)
                    .sheet(fileName)
                    .doWrite(exports);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件编码异常");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件导出异常");
        }

    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Disease related genes", "diseaseRelatedGenes");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Direct Evidence", "directEvidence");
        reader.addHeaderAlias("Inference Network", "inferenceNetwork");
        reader.addHeaderAlias("Source", "source");
        reader.addHeaderAlias("Reference Count", "referenceCount");
        reader.addHeaderAlias("link", "sourceUrl");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("diseaseRelatedGenes", "Disease related genes");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("directEvidence", "Direct Evidence");
        writer.addHeaderAlias("inferenceNetwork", "Inference Network");
        writer.addHeaderAlias("source", "Source");
        writer.addHeaderAlias("referenceCount", "Reference Count");
        writer.addHeaderAlias("sourceUrl", "link");
        return writer;
    }
}

