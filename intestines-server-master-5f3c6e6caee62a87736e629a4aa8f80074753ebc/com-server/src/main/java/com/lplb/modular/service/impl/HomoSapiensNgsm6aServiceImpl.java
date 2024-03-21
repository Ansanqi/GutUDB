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
import com.lplb.modular.mapper.HomoSapiensNgsm6aMapper;
import com.lplb.modular.model.entity.HomoSapiensNgsm6a;
import com.lplb.modular.model.entity.MiRna;
import com.lplb.modular.model.export.HomoSapiensNgsm6aExport;
import com.lplb.modular.model.export.MiRnaExport;
import com.lplb.modular.model.query.HomoSapiensNgsm6aQuery;
import com.lplb.modular.service.HomoSapiensNgsm6aService;
import com.lplb.modular.service.ProjectService;
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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表服务实现类
 */
@Service
@Transactional
public class HomoSapiensNgsm6aServiceImpl extends ServiceImpl<HomoSapiensNgsm6aMapper, HomoSapiensNgsm6a> implements HomoSapiensNgsm6aService {


    private final String tableName = "in_homo_sapiens_ngsm6a";
    private final String filePath = "/03.Transcriptomic/RNA methylation";
    private final String fileName = "HomoSapiens_NGSm6A";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private ProjectService projectService;

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
            List<HomoSapiensNgsm6a> list = reader.readAll(HomoSapiensNgsm6a.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.TRANSCRIPTOMIC.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.RNA_METHYLATION.getId());
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 项目
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                "",
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                HomoSapiensNgsm6a.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
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
    public IPage<HomoSapiensNgsm6a> pageList(HomoSapiensNgsm6aQuery query) {
        // 分页
        Page<HomoSapiensNgsm6a> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<HomoSapiensNgsm6a> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(HomoSapiensNgsm6a::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getSeqnames())) {
            wrapper.eq(HomoSapiensNgsm6a::getSeqnames, query.getSeqnames());
        }
        if (ObjectUtils.isNotEmpty(query.getModification())) {
            wrapper.eq(HomoSapiensNgsm6a::getModification, query.getModification());
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(HomoSapiensNgsm6a::getConditions, query.getConditions());
        }
        if (ObjectUtils.isNotEmpty(query.getTechnique())) {
            wrapper.eq(HomoSapiensNgsm6a::getTechnique, query.getTechnique());
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
    public List<HomoSapiensNgsm6a> listByGeneName(String geneName) {
        return null;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, HomoSapiensNgsm6aQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<HomoSapiensNgsm6a> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(HomoSapiensNgsm6a::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getSeqnames())) {
            wrapper.eq(HomoSapiensNgsm6a::getSeqnames, query.getSeqnames());
        }
        if (ObjectUtils.isNotEmpty(query.getModification())) {
            wrapper.eq(HomoSapiensNgsm6a::getModification, query.getModification());
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(HomoSapiensNgsm6a::getConditions, query.getConditions());
        }
        if (ObjectUtils.isNotEmpty(query.getTechnique())) {
            wrapper.eq(HomoSapiensNgsm6a::getTechnique, query.getTechnique());
        }

        // 查询数据集合
        List<HomoSapiensNgsm6a> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<HomoSapiensNgsm6a> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<HomoSapiensNgsm6aExport> exports = BeanConvertUtil.listConvert(list, HomoSapiensNgsm6aExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_RNA methylation_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), HomoSapiensNgsm6aExport.class)
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
        reader.addHeaderAlias("Number", "number");
        reader.addHeaderAlias("Seqnames", "seqnames");
        reader.addHeaderAlias("Modification", "modification");
        reader.addHeaderAlias("Cell", "cell");
        reader.addHeaderAlias("Condition", "conditions");
        reader.addHeaderAlias("Start", "start");
        reader.addHeaderAlias("End", "end");
        reader.addHeaderAlias("Width", "width");
        reader.addHeaderAlias("Strand", "strand");
        reader.addHeaderAlias("Technique", "technique");
        reader.addHeaderAlias("Resolution", "resolution");
        reader.addHeaderAlias("Project", "project");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("number", "Number");
        writer.addHeaderAlias("seqnames", "Seqnames");
        writer.addHeaderAlias("modification", "Modification");
        writer.addHeaderAlias("cell", "Cell");
        writer.addHeaderAlias("conditions", "Condition");
        writer.addHeaderAlias("start", "Start");
        writer.addHeaderAlias("end", "End");
        writer.addHeaderAlias("width", "Width");
        writer.addHeaderAlias("strand", "Strand");
        writer.addHeaderAlias("technique", "Technique");
        writer.addHeaderAlias("resolution", "Resolution");
        writer.addHeaderAlias("project", "Project");
        return writer;
    }
}

