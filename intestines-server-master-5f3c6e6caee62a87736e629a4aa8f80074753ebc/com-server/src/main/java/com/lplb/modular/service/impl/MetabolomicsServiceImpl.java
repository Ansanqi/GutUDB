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
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.MetabolomicsMapper;
import com.lplb.modular.model.entity.Metabolomics;
import com.lplb.modular.model.entity.Species;
import com.lplb.modular.model.export.MetabolomicsExport;
import com.lplb.modular.model.export.SpeciesExport;
import com.lplb.modular.model.query.MetabolomicsQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.MetabolomicsService;
import com.lplb.modular.service.PmDataService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表服务实现类
 */
@Service
@Transactional
public class MetabolomicsServiceImpl extends ServiceImpl<MetabolomicsMapper, Metabolomics> implements MetabolomicsService {

    private final String tableName = "in_metabolomics";
    private final String filePath = "/08.Metabolomics";
    private final String fileName = "Metabolomics";

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
            List<Metabolomics> list = reader.readAll(Metabolomics.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.METABOLOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.METABOLOMICS.getId());
                        // pmid解析
                        if (ObjectUtils.isNotEmpty(item.getPmidAssociation()) && !"NA".equals(item.getPmidAssociation())) {
                            String pmid = item.getPmidAssociation().substring(item.getPmidAssociation().lastIndexOf("/") + 1);
                            item.setPmid(pmid);
                        }
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 基因
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.METABOLOMICS.getId(),
//                                Metabolomics.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//                        // 疾病
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.METABOLOMICS.getId(),
//                                Metabolomics.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // PMID解析
//                        if (ObjectUtils.isNotEmpty(item.getPmidAssociation()) && !"NA".equals(item.getPmidAssociation())) {
//                            String pmid = item.getPmidAssociation().substring(item.getPmidAssociation().lastIndexOf("/") + 1);
//                            pmDataService.savePmData(OmicsEnums.METABOLOMICS.getId(),
//                                    OmicsCategoryEnums.METABOLOMICS.getId(),
//                                    item.getId(),
//                                    PmDataType.METABOLOMICS.getName(),
//                                    pmid,
//                                    item.getPmidAssociation());
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
    public IPage<Metabolomics> pageList(MetabolomicsQuery query) {
        // 分页
        Page<Metabolomics> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<Metabolomics> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(Metabolomics::getGeneName, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getDisease, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getGutMicrobiota, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getMetabolite, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getSpecies, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getClassification, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getDescription, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getSampleType, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getSubstrate, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getAlteration, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getExperimentalMethod, query.getSearch())
//                    .or()
//                    .like(Metabolomics::getMeasurementTechnique, query.getSearch());
//        }

        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Metabolomics::getGeneName, query.getKeywords())
                    .or()
                    .like(Metabolomics::getSampleType, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Metabolomics::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(Metabolomics::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getAlteration())) {
            wrapper.eq(Metabolomics::getAlteration, query.getAlteration());
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
        } else {
            wrapper.orderByAsc(Metabolomics::getGeneName);
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
    public List<Metabolomics> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<Metabolomics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Metabolomics::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(Metabolomics::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Metabolomics数据统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> metaboliteStatistics() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.metaboliteStatistics();
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
    public void exportExcel(HttpServletResponse response, MetabolomicsQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<Metabolomics> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Metabolomics::getGeneName, query.getKeywords())
                    .or()
                    .like(Metabolomics::getSampleType, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Metabolomics::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(Metabolomics::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getAlteration())) {
            wrapper.eq(Metabolomics::getAlteration, query.getAlteration());
        }

        // 查询数据集合
        List<Metabolomics> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<Metabolomics> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<MetabolomicsExport> exports = BeanConvertUtil.listConvert(list, MetabolomicsExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Metabolomics_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), MetabolomicsExport.class)
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
        reader.addHeaderAlias("Gut Microbiota", "gutMicrobiota");
        reader.addHeaderAlias("Metabolite", "metabolite");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Classification", "classification");
        reader.addHeaderAlias("Description", "description");
        reader.addHeaderAlias("Sample Type", "sampleType");
        reader.addHeaderAlias("Substrate", "substrate");
        reader.addHeaderAlias("Alteration", "alteration");
        reader.addHeaderAlias("Experimental Method", "experimentalMethod");
        reader.addHeaderAlias("Measurement Technique", "measurementTechnique");
        reader.addHeaderAlias("Gene Association", "geneAssociation");
        reader.addHeaderAlias("PMID Association", "pmidAssociation");
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
        writer.addHeaderAlias("gutMicrobiota", "Gut Microbiota");
        writer.addHeaderAlias("metabolite", "Metabolite");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("classification", "Classification");
        writer.addHeaderAlias("description", "Description");
        writer.addHeaderAlias("sampleType", "Sample Type");
        writer.addHeaderAlias("substrate", "Substrate");
        writer.addHeaderAlias("alteration", "Alteration");
        writer.addHeaderAlias("experimentalMethod", "Experimental Method");
        writer.addHeaderAlias("measurementTechnique", "Measurement Technique");
        writer.addHeaderAlias("geneAssociation", "Gene Association");
        writer.addHeaderAlias("pmidAssociation", "PMID Association");
        return writer;
    }
}

