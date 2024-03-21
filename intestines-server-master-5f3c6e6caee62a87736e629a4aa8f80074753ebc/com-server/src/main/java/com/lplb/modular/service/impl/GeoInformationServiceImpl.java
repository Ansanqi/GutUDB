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
import com.lplb.core.consts.SpeciesConstant;
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.GeoInformationMapper;
import com.lplb.modular.model.entity.GeoInformation;
import com.lplb.modular.model.entity.SeMatsJcec;
import com.lplb.modular.model.export.GeoInformationExport;
import com.lplb.modular.model.export.SeMatsJcecExport;
import com.lplb.modular.model.query.GeoInformationQuery;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeoInformationService;
import com.lplb.modular.service.PmDataService;
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
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表服务实现类
 */
@Service
@Transactional
public class GeoInformationServiceImpl extends ServiceImpl<GeoInformationMapper, GeoInformation> implements GeoInformationService {

    private final String tableName = "in_geo_information";
    private final String filePath = "/03.Transcriptomic/Gene expression data";
    private final String fileName = "GEO_information";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private ProjectService projectService;
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
//        // 获取列表
//        List<GeoInformation> list = this.readList(file, "Homo sapiens");
//        List<GeoInformation> list = this.readList(file, "Homo sapiens");
//        list.addAll(this.readList(file, "Mus musculus"));
//        list.addAll(this.readList(file, " Mus musculus"));
//        list.addAll(this.readList(file, "Other"));
        try {

            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 设置表头
            reader = this.readHeaderAlias(reader);

            // 获取列表
            List<GeoInformation> list = reader.readAll(GeoInformation.class);
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
                        item.setCategoryId(OmicsCategoryEnums.GENE_EXPRESSION_DATA.getId());
                        // 设置分类
                        if (SpeciesConstant.HOMO_SAPINES.equals(item.getSpecies().trim())) {
                            item.setInfoType(SpeciesConstant.HOMO_SAPINES);
                        } else if (SpeciesConstant.MUS_MUSCULUS.equals(item.getSpecies().trim())) {
                            item.setInfoType(SpeciesConstant.MUS_MUSCULUS);
                        } else {
                            item.setInfoType(SpeciesConstant.OTHER);
                        }

                        // 去空格
                        item.setSpecies(item.getSpecies().trim());
                        // PMID处理
                        if (ObjectUtils.isNotEmpty(item.getPmid()) && !"NA".equals(item.getPmid())) {
                            String pmidStr = item.getPmid().trim();
                            // 切分
                            List<String> pmids = Arrays.asList(pmidStr.split("、"));
                            List<String> pmidUrls = new ArrayList<>();
                            pmids.forEach(pmid -> {
                                pmidUrls.add(PmDataConstant.PMID_BASE_URL + pmid);
                            });
                            String pmidUrl = pmidUrls.stream().collect(Collectors.joining(","));
                            // pmid重新以,分割保存
                            item.setPmid(pmids.stream().collect(Collectors.joining(",")));
                            item.setPmidUrl(pmidUrl);
                        }

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 项目
//                        projectService.saveProjectAndReq(item.getProject(),
//                                item.getProject(),
//                                "",
//                                OmicsEnums.TRANSCRIPTOMIC.getId().longValue(),
//                                GeoInformation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 疾病
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId().longValue(),
//                                GeoInformation.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // PMID
//                        if (ObjectUtils.isNotEmpty(item.getPmid()) && !"NA".equals(item.getPmid())) {
//                            List<String> pmids = Arrays.asList(item.getPmid().split("、"));
//                            pmids.forEach(pmid -> {
//                                pmDataService.savePmData(OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                        OmicsCategoryEnums.GENE_EXPRESSION_DATA.getId(),
//                                        item.getId(),
//                                        PmDataType.GEO_INFORMATION.getName(),
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
        return true;
    }

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<GeoInformation> pageList(GeoInformationQuery query) {
        // 分页
        Page<GeoInformation> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<GeoInformation> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(GeoInformation::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(GeoInformation::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(GeoInformation::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissueCellLine())) {
            wrapper.eq(GeoInformation::getTissueCellLine, query.getTissueCellLine());
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
     * 根据项目编号查询
     *
     * @param projectNo
     * @return
     */
    @Override
    public GeoInformation getByProject(String projectNo) {
        LambdaQueryWrapper<GeoInformation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GeoInformation::getProject, projectNo);
        return this.getOne(wrapper);
    }

    /**
     * 物种统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> species() {
        LambdaQueryWrapper<GeoInformation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(GeoInformation::getSpecies);
        wrapper.orderByAsc(GeoInformation::getSpecies);
        List<GeoInformation> list = this.list(wrapper);

        if (ObjectUtils.isEmpty(list)) {
            return new LinkedHashMap<>();
        }

        // 各个物种数量
        Map<String, Integer> result = new LinkedHashMap<>();
        list.forEach(item -> {
            String key = item.getSpecies().trim().toLowerCase();
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
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, GeoInformationQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<GeoInformation> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getProject())) {
            String project = query.getProject();
            if (!project.contains("GSE")) {
                project = "GSE" + project;
            }
            wrapper.eq(GeoInformation::getProject, project);
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(GeoInformation::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(GeoInformation::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getTissueCellLine())) {
            wrapper.eq(GeoInformation::getTissueCellLine, query.getTissueCellLine());
        }

        // 查询数据集合
        List<GeoInformation> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<GeoInformation> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<GeoInformationExport> exports = BeanConvertUtil.listConvert(list, GeoInformationExport.class);
        // PMID处理，全面添加一个'，否则，excel会默认未数字
        exports.forEach(item -> {
            item.setPmid("'" + item.getPmid());
        });

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_Gene expression data_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), GeoInformationExport.class)
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

//    /**
//     * 列表读取
//     *
//     * @param file
//     * @param sheetName
//     * @return
//     */
//    private List<GeoInformation> readList(MultipartFile file, String sheetName) {
//        InputStream inputStream = null;
//        try {
//
//            // 文件读取
//            inputStream = file.getInputStream();
//            ExcelReader reader = ExcelUtil.getReader(inputStream, sheetName);
//
//            // 设置表头
//            reader = this.readHeaderAlias(reader);
//
//            // 获取列表
//            List<GeoInformation> list = reader.readAll(GeoInformation.class);
//            // 循环设置分类
//            list.forEach(item -> {
//                // 设置组学ID
//                item.setOmicsId(OmicsEnums.TRANSCRIPTOMIC.getId());
//                // 设置分类ID
//                item.setCategoryId(OmicsCategoryEnums.GENE_EXPRESSION_DATA.getId());
//                // 设置分类
//                item.setInfoType(sheetName);
//
//                // PMID处理
//                if (ObjectUtils.isNotEmpty(item.getPmid()) && !"NA".equals(item.getPmid())) {
//                    // 切分
//                    List<String> pmids = Arrays.asList(item.getPmid().split("、"));
//                    List<String> pmidUrls = new ArrayList<>();
//                    pmids.forEach(pmid -> {
//                        pmidUrls.add(PmDataConstant.PMID_BASE_URL + pmid);
//                    });
//                    String pmidUrl = pmidUrls.stream().collect(Collectors.joining(","));
//                    // pmid重新以,分割保存
//                    item.setPmid(pmids.stream().collect(Collectors.joining(",")));
//                    item.setPmidUrl(pmidUrl);
//                }
//            });
//
//            return list;
//        } catch (Exception e) {
//            throw new ServiceException(500, "文件读取异常");
//        } finally {
//            if (ObjectUtils.isNotEmpty(inputStream)) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    throw new ServiceException(500, "输入流关闭异常");
//                }
//            }
//        }
//    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Project", "project");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Tissue/Cell line", "tissueCellLine");
        reader.addHeaderAlias("CASE", "cases");
        reader.addHeaderAlias("Control", "control");
        reader.addHeaderAlias("PMID", "pmid");
        reader.addHeaderAlias("GEO Association", "geoAssociation");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("project", "Project");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("tissueCellLine", "Tissue/Cell line");
        writer.addHeaderAlias("cases", "CASE");
        writer.addHeaderAlias("control", "Control");
        writer.addHeaderAlias("pmid", "PMID");
        writer.addHeaderAlias("geoAssociation", "GEO Association");
        return writer;
    }

}

