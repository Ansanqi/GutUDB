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
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.ProteomicsMapper;
import com.lplb.modular.model.entity.AlternativeSplicing;
import com.lplb.modular.model.entity.Proteomics;
import com.lplb.modular.model.export.AlternativeSplicingExport;
import com.lplb.modular.model.export.ProteomicsExport;
import com.lplb.modular.model.query.ProteomicsQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.PmDataService;
import com.lplb.modular.service.ProteomicsService;
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
 * @Date（日期）： 2023-07-27 18:35:43
 * @Description（描述）：Proteomics（蛋白质组学）(Proteomics)表服务实现类
 */
@Service
@Transactional
public class ProteomicsServiceImpl extends ServiceImpl<ProteomicsMapper, Proteomics> implements ProteomicsService {

    private final String tableName = "in_proteomics";
    private final String filePath = "/06.Proteomics";
    private final String fileName = "Proteomics";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private PmDataService pmDataService;

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
            List<Proteomics> list = reader.readAll(Proteomics.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.PROTOEOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.PROTE_OMICS.getId());

                        // PMID处理
                        if (ObjectUtils.isNotEmpty(item.getPmid())) {
                            String pmidStr = item.getPmid();
                            List<String> pmidList = new ArrayList<>(Arrays.asList(pmidStr.split(";")));
                            // 去除空格
                            pmidList = pmidList.stream().map(item -> item.trim()).collect(Collectors.toList());

                            // pmidUrl
                            List<String> pmidUrl = pmidList.stream().map(item ->
                                    PmDataConstant.PMID_BASE_URL + item).collect(Collectors.toList());

                            // 重新拼接保存
                            item.setPmid(pmidList.stream().collect(Collectors.joining(",")));
                            item.setPmidUrl(pmidUrl.stream().collect(Collectors.joining(",")));
                        }

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 基因
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.PROTOEOMICS.getId(),
//                                Proteomics.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 疾病
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.PROTOEOMICS.getId(),
//                                Proteomics.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // PMID
//                        if (ObjectUtils.isNotEmpty(item.getPmid())) {
//                            String pmidStr = item.getPmid();
//                            List<String> pmidList = new ArrayList<>(Arrays.asList(pmidStr.split(",")));
//                            pmidList.forEach(pmid -> {
//                                pmDataService.savePmData(OmicsEnums.PROTOEOMICS.getId(),
//                                        OmicsCategoryEnums.PROTE_OMICS.getId(),
//                                        item.getId(),
//                                        PmDataType.PROTE_OMICS.getName(),
//                                        pmid,
//                                        PmDataConstant.PMID_BASE_URL + pmid);
//                            });
//                        }

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
    public IPage<Proteomics> pageList(ProteomicsQuery query) {
        // 分页
        Page<Proteomics> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<Proteomics> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(Proteomics::getGeneName, query.getSearch())
//                    .or()
//                    .like(Proteomics::getDisease, query.getSearch())
//                    .or()
//                    .like(Proteomics::getSapiens, query.getSearch())
//                    .or()
//                    .like(Proteomics::getPosition, query.getSearch())
//                    .or()
//                    .like(Proteomics::getProteinName, query.getSearch())
//                    .or()
//                    .like(Proteomics::getInduction, query.getSearch())
//                    .or()
//                    .like(Proteomics::getPostTranslationalModification, query.getSearch())
//                    .or()
//                    .like(Proteomics::getFunction, query.getSearch())
//                    .or()
//                    .like(Proteomics::getActivityRegulation, query.getSearch())
//                    .or()
//                    .like(Proteomics::getPathway, query.getSearch())
//                    .or()
//                    .like(Proteomics::getPmid, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Proteomics::getGeneName, query.getKeywords())
                    .or()
                    .like(Proteomics::getProteinName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(Proteomics::getSapiens, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Proteomics::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getPosition())) {
            wrapper.eq(Proteomics::getPosition, query.getPosition());
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
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public List<Proteomics> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<Proteomics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Proteomics::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.like(Proteomics::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * 肠道部位统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> position() {
        LambdaQueryWrapper<Proteomics> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Proteomics::getPosition);
        wrapper.orderByAsc(Proteomics::getPosition);
        List<Proteomics> list = this.list(wrapper);

        if (ObjectUtils.isEmpty(list)) {
            return new LinkedHashMap<>();
        }

        // 各个部位数量
        Map<String, Integer> result = new LinkedHashMap<>();
        list.forEach(item -> {
            String key = item.getPosition().trim().toLowerCase();
            Integer value;
            if (result.containsKey(key)) {
                value = result.get(key) + 1;
            } else {
                value = 1;
            }
            result.put(key, value);
        });

        return result;
    }

    /**
     * Protein数据统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> proteinStatistics() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.proteinStatistics();
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
    public void exportExcel(HttpServletResponse response, ProteomicsQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<Proteomics> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Proteomics::getGeneName, query.getKeywords())
                    .or()
                    .like(Proteomics::getProteinName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(Proteomics::getSapiens, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Proteomics::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getPosition())) {
            wrapper.eq(Proteomics::getPosition, query.getPosition());
        }

        // 查询数据集合
        List<Proteomics> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<Proteomics> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ProteomicsExport> exports = BeanConvertUtil.listConvert(list, ProteomicsExport.class);
        // PMID处理，全面添加一个'，否则，excel会默认未数字
        exports.forEach(item -> {
            item.setPmid("'" + item.getPmid());
        });

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Proteomics_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ProteomicsExport.class)
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
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Sapiens", "sapiens");
        reader.addHeaderAlias("Position", "position");
        reader.addHeaderAlias("Protein Name", "proteinName");
        reader.addHeaderAlias("Induction", "induction");
        reader.addHeaderAlias("Length", "length");
        reader.addHeaderAlias("Post Translational Modification", "postTranslationalModification");
        reader.addHeaderAlias("Function", "function");
        reader.addHeaderAlias("Activity Regulation", "activityRegulation");
        reader.addHeaderAlias("Pathway", "pathway");
        reader.addHeaderAlias("PMID", "pmid");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("sapiens", "Sapiens");
        writer.addHeaderAlias("position", "Position");
        writer.addHeaderAlias("proteinName", "Protein Name");
        writer.addHeaderAlias("induction", "Induction");
        writer.addHeaderAlias("length", "Length");
        writer.addHeaderAlias("postTranslationalModification", "Post Translational Modification");
        writer.addHeaderAlias("function", "Function");
        writer.addHeaderAlias("activityRegulation", "Activity Regulation");
        writer.addHeaderAlias("pathway", "Pathway");
        writer.addHeaderAlias("pmid", "PMID");
        writer.addHeaderAlias("pmidUrl", "pmidUrl");
        return writer;
    }

}

