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
import com.lplb.modular.mapper.ApaPametaMergeMapper;
import com.lplb.modular.model.entity.A3ssMatsJece;
import com.lplb.modular.model.entity.ApaPametaMerge;
import com.lplb.modular.model.entity.SingleCellGeneExpressData;
import com.lplb.modular.model.export.ApaPametaMergeExport;
import com.lplb.modular.model.export.SingleCellGeneExpressDataExport;
import com.lplb.modular.model.query.ApaPametaMergeQuery;
import com.lplb.modular.service.ApaPametaMergeService;
import com.lplb.modular.service.ProjectService;
import com.lplb.modular.service.SampleService;
import com.lplb.modular.service.TissueCellLineService;
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
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：APA-pameta-merge(ApaPametaMerge)表服务实现类
 */
@Service
@Transactional
public class ApaPametaMergeServiceImpl extends ServiceImpl<ApaPametaMergeMapper, ApaPametaMerge> implements ApaPametaMergeService {

    private final String tableName = "in_apa_pameta_merge";
    private final String filePath = "/05.Single cell omics/Single-cell alternative polyadenylation";
    private final String fileName = "APA-pameta-merge";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private ProjectService projectService;
    @Resource
    private TissueCellLineService tissueCellLineService;
    @Resource
    private SampleService sampleService;

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
            List<ApaPametaMerge> list = reader.readAll(ApaPametaMerge.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.SINGLECELLOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.SINGLE_CELL_PMICS.getId());

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 项目
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                "",
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                ApaPametaMerge.class.getName(),
//                                tableName,
//                                filePath,
//                                fileName,
//                                item.getId());
//
//                        // 组织
//                        tissueCellLineService.savaTissueAndSeq(item.getTissue(),
//                                item.getTissue(),
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                ApaPametaMerge.class.getName(),
//                                tableName,
//                                filePath,
//                                fileName,
//                                item.getId());
//
//                        // 样本
//                        sampleService.saveSampleAndSeq(item.getSampleId(),
//                                item.getSampleId(),
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                ApaPametaMerge.class.getName(),
//                                tableName,
//                                filePath,
//                                fileName,
//                                item.getId());

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
    public IPage<ApaPametaMerge> pageList(ApaPametaMergeQuery query) {
        // 分页
        Page<ApaPametaMerge> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<ApaPametaMerge> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(ApaPametaMerge::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getSampleId())) {
            String sampleId = query.getSampleId();
            if (!sampleId.contains("GSM")) {
                sampleId = "GSM" + sampleId;
            }
            wrapper.eq(ApaPametaMerge::getSampleId, sampleId);
        }
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(ApaPametaMerge::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getOrganism())) {
            wrapper.eq(ApaPametaMerge::getOrganism, query.getOrganism());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(ApaPametaMerge::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(ApaPametaMerge::getConditions, query.getConditions());
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
     * @return
     */
    @Override
    public List<ApaPametaMerge> listByGeneName(String geneName) {
        LambdaQueryWrapper<ApaPametaMerge> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ApaPametaMerge::getGen)
        return null;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, ApaPametaMergeQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<ApaPametaMerge> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(ApaPametaMerge::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getSampleId())) {
            String sampleId = query.getSampleId();
            if (!sampleId.contains("GSM")) {
                sampleId = "GSM" + sampleId;
            }
            wrapper.eq(ApaPametaMerge::getSampleId, sampleId);
        }
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(ApaPametaMerge::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getOrganism())) {
            wrapper.eq(ApaPametaMerge::getOrganism, query.getOrganism());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(ApaPametaMerge::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(ApaPametaMerge::getConditions, query.getConditions());
        }

        // 查询数据集合
        List<ApaPametaMerge> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<ApaPametaMerge> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ApaPametaMergeExport> exports = BeanConvertUtil.listConvert(list, ApaPametaMergeExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Single cell omics_Alternative polvadenylation_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ApaPametaMergeExport.class)
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
        reader.addHeaderAlias("Ensemble ID", "ensembleId");
        reader.addHeaderAlias("Organism", "organism");
        reader.addHeaderAlias("Tissue", "tissue");
        reader.addHeaderAlias("Condition", "conditions");
        reader.addHeaderAlias("Protocol", "protocol");
        reader.addHeaderAlias("chr", "chr");
        reader.addHeaderAlias("start", "start");
        reader.addHeaderAlias("end", "end");
        reader.addHeaderAlias("strand", "strand");
        reader.addHeaderAlias("PA Id", "paId");
        reader.addHeaderAlias("Project", "project");
        reader.addHeaderAlias("Sample ID", "sampleId");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("ensembleId", "Ensemble ID");
        writer.addHeaderAlias("organism", "Organism");
        writer.addHeaderAlias("tissue", "Tissue");
        writer.addHeaderAlias("conditions", "Condition");
        writer.addHeaderAlias("protocol", "Protocol");
        writer.addHeaderAlias("chr", "chr");
        writer.addHeaderAlias("start", "start");
        writer.addHeaderAlias("end", "end");
        writer.addHeaderAlias("strand", "strand");
        writer.addHeaderAlias("paId", "PA Id");
        writer.addHeaderAlias("project", "Project");
        writer.addHeaderAlias("sampleId", "Sample ID");
        return writer;
    }
}

