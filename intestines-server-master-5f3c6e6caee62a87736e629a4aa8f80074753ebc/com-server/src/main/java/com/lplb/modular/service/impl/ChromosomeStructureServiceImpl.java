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
import com.lplb.modular.mapper.ChromosomeStructureMapper;
import com.lplb.modular.model.entity.ChromosomeStructure;
import com.lplb.modular.model.export.ChromosomeStructureExport;
import com.lplb.modular.model.query.ChromosomeStructureQuery;
import com.lplb.modular.service.ChromosomeStructureService;
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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chromosome structure（染色体结构）(ChromosomeStructure)表服务实现类
 */
@Service
@Transactional
public class ChromosomeStructureServiceImpl extends ServiceImpl<ChromosomeStructureMapper, ChromosomeStructure> implements ChromosomeStructureService {

    private final String tableName = "in_chromosome_structure";
    private final String filePath = "/01.Epigenomics/Chromosome structure";
    private final String fileName = "Chromosome_structure";

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
            List<ChromosomeStructure> list = reader.readAll(ChromosomeStructure.class);

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
                        item.setCategoryId(OmicsCategoryEnums.CHROMOSOME_STRUCTURE.getId());
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到Project，统计放入数据库
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                item.getProjectUrl(),
//                                OmicsEnums.EPIGENOMICS.getId(),
//                                ChromosomeStructure.class.getName(),
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
     * 列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<ChromosomeStructure> pageList(ChromosomeStructureQuery query) {
        // 分页
        Page<ChromosomeStructure> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<ChromosomeStructure> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtils.isNotEmpty(query.getFileName()), ChromosomeStructure::getFileName, query.getFileName());
//        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
//            wrapper.like(ChromosomeStructure::getHiCDatasetTitle, query.getKeywords())
//                    .or()
//                    .like(ChromosomeStructure::getOrganism, query.getKeywords())
//                    .or()
//                    .like(ChromosomeStructure::getGsdbId, query.getKeywords())
//                    .or()
//                    .like(ChromosomeStructure::getProject, query.getKeywords());
//        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;
            if ("structure3d".equals(order[0])) {
                colum = "structure_3d";
            } else {
                colum = StringUtils.camelToUnderline(order[0]);
            }

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
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, ChromosomeStructureQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<ChromosomeStructure> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtils.isNotEmpty(query.getFileName()), ChromosomeStructure::getFileName, query.getFileName());

        // 查询数据集合
        List<ChromosomeStructure> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<ChromosomeStructure> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ChromosomeStructureExport> exports = BeanConvertUtil.listConvert(list, ChromosomeStructureExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Epigenomics_Chromosome Structure_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ChromosomeStructureExport.class)
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
     * @return
     */
    @Override
    public List<ChromosomeStructure> listByGeneName(String geneName) {
        LambdaQueryWrapper<ChromosomeStructure> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ChromosomeStructure::getG)
        return null;
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Filename", "fileName");
        reader.addHeaderAlias("Hi-C Dataset Title", "hiCDatasetTitle");
        reader.addHeaderAlias("3D Structure", "structure3d");
        reader.addHeaderAlias("Structure link", "structure3dUrl");
        reader.addHeaderAlias("Organism", "organism");
        reader.addHeaderAlias("GSDB ID", "gsdbId");
        reader.addHeaderAlias("Project", "project");
        reader.addHeaderAlias("Project link", "projectUrl");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {

        writer.addHeaderAlias("fileName", "Filename");
        writer.addHeaderAlias("hiCDatasetTitle", "Hi-C Dataset Title");
        writer.addHeaderAlias("structure3d", "3D Structure");
        writer.addHeaderAlias("organism", "Organism");
        writer.addHeaderAlias("gsdbId", "GSDB ID");
        writer.addHeaderAlias("project", "Project");
        writer.addHeaderAlias("structure3dUrl", "Structure link");
        writer.addHeaderAlias("projectUrl", "Project link");
        return writer;
    }
}

