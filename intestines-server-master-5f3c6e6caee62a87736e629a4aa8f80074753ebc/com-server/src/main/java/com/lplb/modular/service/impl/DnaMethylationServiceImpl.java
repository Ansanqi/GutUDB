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
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.DnaMethylationMapper;
import com.lplb.modular.model.entity.DnaMethylation;
import com.lplb.modular.model.export.DnaMethylationExport;
import com.lplb.modular.model.query.DnaMethylationQuery;
import com.lplb.modular.model.vo.DnaMethylationVo;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.*;
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
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)表服务实现类
 */
@Service
@Transactional
public class DnaMethylationServiceImpl extends ServiceImpl<DnaMethylationMapper, DnaMethylation> implements DnaMethylationService {

    private final String tableName = "in_dna_methylation";
    private final String filePath = "/01.Epigenomics/DNA methylation";
    private final String fileName = "DNA_methylation";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private ProjectService projectService;
    @Resource
    private SampleService sampleService;
    @Resource
    private TissueCellLineService tissueCellLineService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private FiledColorService filedColorService;

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
            List<DnaMethylation> list = reader.readAll(DnaMethylation.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.EPIGENOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.DNA_METHYLATION.getId());
                        flag.set(save(item));
                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                DnaMethylation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到Project，统计放入数据库
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                item.getProjectUrl(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                DnaMethylation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到Sample，统计放入数据库
//                        sampleService.saveSampleAndSeq(item.getSampleId(),
//                                item.getSampleName(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                DnaMethylation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到组织细胞系，统计放入数据库
//                        tissueCellLineService.savaTissueAndSeq(
//                                item.getTissueCellLine(),
//                                item.getTissueCellLine(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                DnaMethylation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );
//
//                        // 该数据关系到疾病，统计放入数据库
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                DnaMethylation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );

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
    public IPage<DnaMethylationVo> pageList(DnaMethylationQuery query) {
        // 分页
        Page<DnaMethylation> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<DnaMethylation> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(DnaMethylation::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(DnaMethylation::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getSampleId())) {
            String sampleId = query.getSampleId();
            if (!sampleId.contains("GSM")) {
                sampleId = "GSM" + sampleId;
            }
            wrapper.eq(DnaMethylation::getSampleId, sampleId);
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(DnaMethylation::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(DnaMethylation::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissueCellLine())) {
            wrapper.eq(DnaMethylation::getTissueCellLine, query.getTissueCellLine());
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

        IPage<DnaMethylationVo> result = BeanConvertUtil.pageConvert(page, DnaMethylationVo.class);
        result.getRecords().forEach(item -> {
            // 查询颜色
            String color = filedColorService.findByType("in_dna_methylation", "average_methylation_level", String.valueOf(item.getAverageMethylationLevel().stripTrailingZeros().toPlainString()));
            item.setColor(color);
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
    public void exportExcel(HttpServletResponse response, DnaMethylationQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<DnaMethylation> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(DnaMethylation::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(DnaMethylation::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getSampleId())) {
            String sampleId = query.getSampleId();
            if (!sampleId.contains("GSM")) {
                sampleId = "GSM" + sampleId;
            }
            wrapper.eq(DnaMethylation::getSampleId, sampleId);
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(DnaMethylation::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(DnaMethylation::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissueCellLine())) {
            wrapper.eq(DnaMethylation::getTissueCellLine, query.getTissueCellLine());
        }

        // 查询数据集合
        List<DnaMethylation> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<DnaMethylation> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<DnaMethylationExport> exports = BeanConvertUtil.listConvert(list, DnaMethylationExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Epigenomics_DNA Methylation_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), DnaMethylationExport.class)
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
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public List<DnaMethylation> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<DnaMethylation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DnaMethylation::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(DnaMethylation::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Top 10 Genes in DNA Methylation
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10GenesInDnaMethylation() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10GenesInDnaMethylation();

        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Ensemble ID", "ensembleId");
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Chromosome", "chromosome");
        reader.addHeaderAlias("Start Position", "startPosition");
        reader.addHeaderAlias("End Position", "endPosition");
        reader.addHeaderAlias("CpG Length", "cpgLength");
        reader.addHeaderAlias("CpG Number", "cpgNumber");
        reader.addHeaderAlias("Average Methylation Level", "averageMethylationLevel");
        reader.addHeaderAlias("Strand", "strand");
        reader.addHeaderAlias("Project", "project");
        reader.addHeaderAlias("Sample ID", "sampleId");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Genomics Location", "genomicsLocation");
        reader.addHeaderAlias("Sample Name", "sampleName");
        reader.addHeaderAlias("Description", "description");
        reader.addHeaderAlias("Tissue/Cell Line", "tissueCellLine");
        reader.addHeaderAlias("Gender", "gender");
        reader.addHeaderAlias("Age", "age");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Platform", "platform");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("ensembleId", "Ensemble ID");
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("chromosome", "Chromosome");
        writer.addHeaderAlias("startPosition", "Start Position");
        writer.addHeaderAlias("endPosition", "End Position");
        writer.addHeaderAlias("cpgLength", "CpG Length");
        writer.addHeaderAlias("cpgNumber", "CpG Number");
        writer.addHeaderAlias("averageMethylationLevel", "Average Methylation Level");
        writer.addHeaderAlias("strand", "Strand");
        writer.addHeaderAlias("project", "Project");
        writer.addHeaderAlias("sampleId", "Sample ID");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("genomicsLocation", "Genomics Location");
        writer.addHeaderAlias("sampleName", "Sample Name");
        writer.addHeaderAlias("description", "Description");
        writer.addHeaderAlias("tissueCellLine", "Tissue/Cell Line");
        writer.addHeaderAlias("gender", "Gender");
        writer.addHeaderAlias("age", "Age");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("platform", "Platform");
        return writer;
    }
}

