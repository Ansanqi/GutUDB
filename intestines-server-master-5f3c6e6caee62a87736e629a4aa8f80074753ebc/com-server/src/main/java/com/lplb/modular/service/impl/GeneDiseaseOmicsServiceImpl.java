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
import com.lplb.core.consts.PmDataConstant;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.GeneDiseaseOmicsMapper;
import com.lplb.modular.model.entity.*;
import com.lplb.modular.model.export.GeneDiseaseOmicsExport;
import com.lplb.modular.model.query.GeneDiseaseOmicsQuery;
import com.lplb.modular.model.vo.*;
import com.lplb.modular.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表服务实现类
 */
@Service
public class GeneDiseaseOmicsServiceImpl extends ServiceImpl<GeneDiseaseOmicsMapper, GeneDiseaseOmics> implements GeneDiseaseOmicsService {

    private final String filePath = "/11.Gene_disease_omics";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private ColonDiseaseService colonDiseaseService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private OmicsLevelService omicsLevelService;
    @Resource
    private GeneService geneService;
    private final String fileName = "Genera";

    /**
     * Colon Disease表头搜索数据
     *
     * @return
     */
    @Override
    public ColonDiseaseSearchHeaderVo colonDiseaseSearchHeader() {
        // 返回结果
        ColonDiseaseSearchHeaderVo result = new ColonDiseaseSearchHeaderVo();

        // Colon Disease表头搜索疾病对象
        List<ColonDiseaseSearchVo> diseases = this.getColonDisease();
        result.setDiseases(diseases);

        // Colon Disease表头搜索Omics level对象
        ColonDiseaseOmicsLevelVo all = new ColonDiseaseOmicsLevelVo();
        all.setName("All");
        List<ColonDiseaseOmicsLevelVo> omicsLevels = new ArrayList<>();
        omicsLevels.add(all);
        omicsLevels.addAll(this.getOmicsLevels(0L));
        result.setOmicsLevels(omicsLevels);

        // Colon Disease表头搜索Hot genes
        List<ColonDiseaseHotGenesVo> hotGeness = this.getHotGeness();
        result.setHotGeness(hotGeness);
        return result;
    }

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

            // 获取列表
            List<Map<String, Object>> map = reader.readAll();
            map.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        GeneDiseaseOmics omics = new GeneDiseaseOmics();
                        if (ObjectUtils.isNotEmpty(item.get("Disease related genes"))) {
                            omics.setDiseaseRelatedGenes(String.valueOf(item.get("Disease related genes")));
                        }
                        if (ObjectUtils.isNotEmpty(item.get("Disease"))) {
                            omics.setDisease(String.valueOf(item.get("Disease")));
                        }
                        if (ObjectUtils.isNotEmpty(item.get("Omics"))) {
                            omics.setOmics(String.valueOf(item.get("Omics")));
                        }
                        if (ObjectUtils.isNotEmpty(item.get("Type"))) {
                            omics.setTypess(String.valueOf(item.get("Type")));
                        }
                        if (ObjectUtils.isNotEmpty(item.get("omicsLevel"))) {
                            omics.setOmicsLevel(String.valueOf(item.get("omicsLevel")));
                        }
                        if (ObjectUtils.isNotEmpty(item.get("url"))) {
                            omics.setUrl(String.valueOf(item.get("url")));
                        }

                        // Source处理
                        String source = "";
                        if (ObjectUtils.isNotEmpty(item.get("Source")) && !"NA".equals(item.get("Source")) && !"null".equals(item.get("Source"))) {
                            source = String.valueOf(item.get("Source"));
                        }
                        // 去除[uid]
                        if (ObjectUtils.isNotEmpty(source)) {
                            source = source.replace("[uid]", "");
                        }
                        // 查询所有的PMID
                        for (int i = 0; i < 113; i++) {
                            int index = i + 1;
                            String key = "PMID" + index;
                            if (item.containsKey(key)) {
                                if (ObjectUtils.isNotEmpty(item.get(key))) {
                                    String value = String.valueOf(item.get(key));
                                    if (!"NA".equals(value)) {
                                        if (ObjectUtils.isNotEmpty(source)) {
                                            source = source + "," + value;
                                        } else {
                                            source = value;
                                        }
                                    }
                                }
                            }
                        }

                        if (ObjectUtils.isNotEmpty(source)) {
                            // 分解
                            List<String> pmids = new ArrayList<>(Arrays.asList(source.split(",")));
                            // 去除空格
                            pmids = pmids.stream().map(e -> e.trim()).collect(Collectors.toList());
                            // url
                            List<String> pmidUrls = pmids.stream().map(e -> PmDataConstant.PMID_BASE_URL + e).collect(Collectors.toList());
                            omics.setSources(pmids.stream().collect(Collectors.joining(",")));
                            omics.setUrl(pmidUrls.stream().collect(Collectors.joining(",")));
                        }
                        save(omics);


                        // TODO 统计数据无用，暂时注释
//                        // 基因
//                        if (ObjectUtils.isNotEmpty(item.getDiseaseRelatedGenes()) && !"NA".equals(item.getDiseaseRelatedGenes())) {
//                            geneService.saveGeneAndSeq(item.getDiseaseRelatedGenes(),
//                                    item.getDiseaseRelatedGenes(),
//                                    null,
//                                    GeneDiseaseOmics.class.getName(),
//                                    null,
//                                    filePath,
//                                    file.getOriginalFilename(),
//                                    item.getId());
//                        }
//                        // 疾病
//                        if (ObjectUtils.isNotEmpty(item.getDisease()) && !"NA".equals(item.getDisease())) {
//                            List<String> diseases = new ArrayList<>(Arrays.asList(item.getDisease().split(",")));
//                            diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                    item.getDisease(),
//                                    null,
//                                    GeneDiseaseOmics.class.getName(),
//                                    null,
//                                    filePath,
//                                    file.getOriginalFilename(),
//                                    item.getId());
//                        }
                    }
                });
            });
            return true;
        } catch (
                IOException e) {
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
    public IPage<GeneDiseaseOmics> pageList(GeneDiseaseOmicsQuery query) {
        // 分页
        Page<GeneDiseaseOmics> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<GeneDiseaseOmics> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getDiseaseName()) && !"All".equals(query.getDiseaseName())) {
            wrapper.eq(GeneDiseaseOmics::getDisease, query.getDiseaseName());
        }
        if (ObjectUtils.isNotEmpty(query.getOmicsLevel()) && !"All".equals(query.getOmicsLevel())) {
            wrapper.and(w -> w.eq(GeneDiseaseOmics::getOmicsLevel, query.getOmicsLevel())
                    .or()
                    .eq(GeneDiseaseOmics::getTypess, query.getOmicsLevel()));
        } else {
            // 由于不能影响基因详情和项目详情数据，此处需要手动屏蔽掉COAD(GEPIA2)
            wrapper.ne(GeneDiseaseOmics::getOmicsLevel, "COAD(GEPIA2)");
        }
        if (ObjectUtils.isNotEmpty(query.getHotGenes()) && !"All".equals(query.getHotGenes())) {
            wrapper.eq(GeneDiseaseOmics::getDiseaseRelatedGenes, query.getHotGenes());
        }

//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(GeneDiseaseOmics::getDisease, query.getSearch())
//                    .or()
//                    .like(GeneDiseaseOmics::getOmicsLevel, query.getSearch())
//                    .or()
//                    .like(GeneDiseaseOmics::getDiseaseRelatedGenes, query.getSearch())
//                    .or()
//                    .like(GeneDiseaseOmics::getOmics, query.getSearch())
//                    .or()
//                    .like(GeneDiseaseOmics::getSources, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(GeneDiseaseOmics::getDiseaseRelatedGenes, query.getKeywords());
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
        page.getRecords().forEach(item -> {
            if (ObjectUtils.isEmpty(item.getSources())) {
                item.setSources(item.getUrl());
            }
        });
        return page;
    }

    /**
     * 肠道疾病统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> intestinalDiseases() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.intestinalDiseases();
        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, GeneDiseaseOmicsQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<GeneDiseaseOmics> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getDiseaseName()) && !"All".equals(query.getDiseaseName())) {
            wrapper.eq(GeneDiseaseOmics::getDisease, query.getDiseaseName());
        }
        if (ObjectUtils.isNotEmpty(query.getOmicsLevel()) && !"All".equals(query.getOmicsLevel())) {
            wrapper.eq(GeneDiseaseOmics::getOmicsLevel, query.getOmicsLevel());
        }
        if (ObjectUtils.isNotEmpty(query.getHotGenes()) && !"All".equals(query.getHotGenes())) {
            wrapper.eq(GeneDiseaseOmics::getDiseaseRelatedGenes, query.getHotGenes());
        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(GeneDiseaseOmics::getDiseaseRelatedGenes, query.getKeywords());
        }

        // 查询数据集合
        List<GeneDiseaseOmics> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<GeneDiseaseOmics> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<GeneDiseaseOmicsExport> exports = BeanConvertUtil.listConvert(list, GeneDiseaseOmicsExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Home_Intestinal Diseases_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), GeneDiseaseOmicsExport.class)
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
     * Colon Disease表头搜索Hot genes
     *
     * @return
     */
    private List<ColonDiseaseHotGenesVo> getHotGeness() {
        List<ColonDiseaseHotGenesVo> result = new ArrayList<>();
        LambdaQueryWrapper<Gene> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Gene::getFrequency);
        Page<Gene> page = new Page<>(1, 15);

        page = geneService.page(page, wrapper);
        // allCount
        AtomicInteger allCount = new AtomicInteger();
        page.getRecords().forEach(item -> {
            ColonDiseaseHotGenesVo vo = new ColonDiseaseHotGenesVo();
            vo.setName(item.getName());
            vo.setCount(item.getFrequency());
            result.add(vo);
            allCount.addAndGet(vo.getCount());
        });

        ColonDiseaseHotGenesVo all = new ColonDiseaseHotGenesVo();
        all.setName("All");
        all.setCount(allCount.get());

        List<ColonDiseaseHotGenesVo> hot = new ArrayList<>();
        hot.add(all);
        hot.addAll(result);

        return hot;
    }

    /**
     * Colon Disease表头搜索疾病对象
     *
     * @return
     */
    private List<ColonDiseaseSearchVo> getColonDisease() {
        List<ColonDiseaseSearchVo> diseases = new ArrayList<>();

        // 先查询肠道疾病name列表
        LambdaQueryWrapper<ColonDisease> colonDiseaseWrapper = new LambdaQueryWrapper<>();
        colonDiseaseWrapper.select(ColonDisease::getName);
        colonDiseaseWrapper.orderByAsc(ColonDisease::getName);
        colonDiseaseWrapper.groupBy(ColonDisease::getName);
        List<ColonDisease> colonDiseases = colonDiseaseService.list(colonDiseaseWrapper);
        List<String> diseaseNames = colonDiseases.stream().map(item -> item.getName()).collect(Collectors.toList());

        // 查询疾病
        LambdaQueryWrapper<Disease> diseaseWrapper = new LambdaQueryWrapper<>();
        diseaseWrapper.in(Disease::getName, diseaseNames);
        diseaseWrapper.orderByDesc(Disease::getFrequency);
        List<Disease> diseaseList = diseaseService.list(diseaseWrapper);

        // all
        ColonDiseaseSearchVo all = new ColonDiseaseSearchVo();
        all.setName("All");
        AtomicInteger count = new AtomicInteger(0);
        diseaseList.forEach(item -> {
            count.addAndGet(item.getFrequency());
        });
        all.setCount(count.get());
        diseases.add(all);

        // 疾病列表
        diseaseList.forEach(item -> {
            ColonDiseaseSearchVo vo = new ColonDiseaseSearchVo();
            vo.setName(item.getName());
            vo.setCount(item.getFrequency());
            diseases.add(vo);
        });
        return diseases;
    }

    /**
     * Colon Disease表头搜索Omics level对象
     *
     * @return
     */
    private List<ColonDiseaseOmicsLevelVo> getOmicsLevels(Long parentId) {
        List<ColonDiseaseOmicsLevelVo> result = new ArrayList<>();
        // 查询为0的级别
        LambdaQueryWrapper<OmicsLevel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OmicsLevel::getParentId, parentId);
        // 由于不能影响基因详情和项目详情数据，此处需要手动屏蔽掉COAD(GEPIA2)
        wrapper.ne(OmicsLevel::getName, "COAD(GEPIA2)");
        wrapper.orderByAsc(OmicsLevel::getOrderBy);
        List<OmicsLevel> omicsLevels = omicsLevelService.list(wrapper);

        // 循环
        omicsLevels.forEach(item -> {
            ColonDiseaseOmicsLevelVo vo = new ColonDiseaseOmicsLevelVo();
            vo.setName(item.getName());
            // 子列表
            List<ColonDiseaseOmicsLevelVo> childs = this.getOmicsLevels(item.getId());
            if (ObjectUtils.isNotEmpty(childs)) {
                vo.setChild(childs);
            }
            result.add(vo);
        });
        return result;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("diseaseRelatedGenes", "Disease related genes");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("omics", "Omics");
        writer.addHeaderAlias("sources", "Source");
        writer.addHeaderAlias("url", "url");
        writer.addHeaderAlias("omicsLevel", "omicsLevel");
        writer.addHeaderAlias("typess", "Type");
        return writer;
    }
}

