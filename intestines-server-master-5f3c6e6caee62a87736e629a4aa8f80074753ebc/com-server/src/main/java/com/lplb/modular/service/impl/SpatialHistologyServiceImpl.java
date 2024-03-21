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
import com.lplb.modular.mapper.SpatialHistologyMapper;
import com.lplb.modular.model.entity.HomoSapiensNgsm6a;
import com.lplb.modular.model.entity.SpatialHistology;
import com.lplb.modular.model.export.HomoSapiensNgsm6aExport;
import com.lplb.modular.model.export.SpatialHistologyExport;
import com.lplb.modular.model.query.SpatialHistologyQuery;
import com.lplb.modular.model.vo.SpatialHistologyVo;
import com.lplb.modular.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：Spatial histology(SpatialHistology)表服务实现类
 */
@Service
@Transactional
public class SpatialHistologyServiceImpl extends ServiceImpl<SpatialHistologyMapper, SpatialHistology> implements SpatialHistologyService {

    private final String tableName = "in_spatial_histology";
    private final String filePath = "/04.Spatial omics/Spatial histology";
    @Value("${file.requestPath}")
    private String fileRequestPath;
    private final String fileName = "Spatial_histology";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private TissueCellLineService tissueCellLineService;
    @Resource
    private ProjectService projectService;
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
            List<SpatialHistology> list = reader.readAll(SpatialHistology.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.SPATIALOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.SPATIAL_PMICS.getId());
                        // 项目重新分割
                        List<String> project = new ArrayList<>(Arrays.asList(item.getProject().split(";")));
                        // 去空格
                        project.stream().map(i -> i.trim()).collect(Collectors.toList());
                        // 重新以,分割
                        String projectStr = project.stream().collect(Collectors.joining(","));
                        item.setProject(projectStr);
                        item.setProjectUrl(projectStr);
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.SPATIALOMICS.getId(),
//                                SpatialHistology.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到项目，统计放入数据库
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                "",
//                                OmicsEnums.SPATIALOMICS.getId(),
//                                SpatialHistology.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );
//
//                        // 组织细胞系
//                        tissueCellLineService.savaTissueAndSeq(item.getTissue(),
//                                item.getTissue(),
//                                OmicsEnums.SPATIALOMICS.getId(),
//                                SpatialHistology.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 样本
//                        sampleService.saveSampleAndSeq(item.getSampleId(),
//                                item.getGeneName(),
//                                OmicsEnums.SPATIALOMICS.getId(),
//                                SpatialHistology.class.getName(),
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
    public IPage<SpatialHistologyVo> pageList(SpatialHistologyQuery query) {
        // 分页
        Page<SpatialHistology> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<SpatialHistology> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(SpatialHistology::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(SpatialHistology::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SpatialHistology::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getBiotechCategories())) {
            wrapper.eq(SpatialHistology::getBiotechCategories, query.getBiotechCategories());
        }
        if (ObjectUtils.isNotEmpty(query.getBiotech())) {
            wrapper.eq(SpatialHistology::getBiotech, query.getBiotech());
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

        IPage<SpatialHistologyVo> result = BeanConvertUtil.pageConvert(page, SpatialHistologyVo.class);
        result.getRecords().forEach(item -> {
            String annotationImg = fileRequestPath + "imgs/IntestineDB/04.Spatial omics/Spatial histology/"
                    + item.getSampleId() + "/Annotation/annotation.png";
            String expressionImg = fileRequestPath + "imgs/IntestineDB/04.Spatial omics/Spatial histology/"
                    + item.getSampleId() + "/Expression/"
                    + item.getGeneName() + ".png";
            item.setAnnotationImg(annotationImg);
            item.setExpressionImg(expressionImg);
        });

        return result;
    }

    /**
     * 详情查询
     *
     * @param id
     * @return
     */
    @Override
    public SpatialHistologyVo details(Long id) {
        SpatialHistology spatialHistology = this.getById(id);
        if (ObjectUtils.isEmpty(spatialHistology)) {
            throw new ServiceException(500, "数据不存在");
        }
        SpatialHistologyVo result = BeanConvertUtil.convert(spatialHistology, SpatialHistologyVo.class);

        // 查询图片
        String annotationImg = fileRequestPath + "imgs/IntestineDB/04.Spatial omics/Spatial histology/"
                + spatialHistology.getSampleId() + "/Annotation/annotation.png";
        String expressionImg = fileRequestPath + "imgs/IntestineDB/04.Spatial omics/Spatial histology/"
                + spatialHistology.getSampleId() + "/Expression/"
                + spatialHistology.getGeneName() + ".png";

        result.setAnnotationImg(annotationImg);
        result.setExpressionImg(expressionImg);
        return result;
    }

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<SpatialHistology> listByGeneName(String geneName) {
        LambdaQueryWrapper<SpatialHistology> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpatialHistology::getGeneName, geneName);
        return this.list(wrapper);
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, SpatialHistologyQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<SpatialHistology> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(SpatialHistology::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(SpatialHistology::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SpatialHistology::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getBiotechCategories())) {
            wrapper.eq(SpatialHistology::getBiotechCategories, query.getBiotechCategories());
        }
        if (ObjectUtils.isNotEmpty(query.getBiotech())) {
            wrapper.eq(SpatialHistology::getBiotech, query.getBiotech());
        }

        // 查询数据集合
        List<SpatialHistology> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<SpatialHistology> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<SpatialHistologyExport> exports = BeanConvertUtil.listConvert(list, SpatialHistologyExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Spatialomic_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), SpatialHistologyExport.class)
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
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Tissue", "tissue");
        reader.addHeaderAlias("Biotech Categories", "biotechCategories");
        reader.addHeaderAlias("Expression Range", "expressionRange");
        reader.addHeaderAlias("Biotech", "biotech");
        reader.addHeaderAlias("N Unit", "nUnit");
        reader.addHeaderAlias("Title", "title");
        reader.addHeaderAlias("Project", "project");
        reader.addHeaderAlias("Sample ID", "sampleId");
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
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("tissue", "Tissue");
        writer.addHeaderAlias("biotechCategories", "Biotech Categories");
        writer.addHeaderAlias("expressionRange", "Expression Range");
        writer.addHeaderAlias("biotech", "Biotech");
        writer.addHeaderAlias("nUnit", "N Unit");
        writer.addHeaderAlias("title", "Title");
        writer.addHeaderAlias("project", "Project");
        writer.addHeaderAlias("sampleId", "Sample ID");
        return writer;
    }
}

