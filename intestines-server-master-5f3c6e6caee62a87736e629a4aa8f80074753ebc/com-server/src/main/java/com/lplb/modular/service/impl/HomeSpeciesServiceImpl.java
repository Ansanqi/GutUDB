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
import com.lplb.modular.mapper.HomeSpeciesMapper;
import com.lplb.modular.model.entity.HomeSpecies;
import com.lplb.modular.model.export.HomeSpeciesExport;
import com.lplb.modular.model.query.HomeSpeciesQuery;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.HomeSpeciesService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 23:02:23
 * @Description（描述）：Home | Species(HomeSpecies)表服务实现类
 */
@Service
public class HomeSpeciesServiceImpl extends ServiceImpl<HomeSpeciesMapper, HomeSpecies> implements HomeSpeciesService {
    private final String tableName = "in_home_species";
    private final String filePath = "/10.Species";
    private final String fileName = "species";

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
            List<HomeSpecies> list = reader.readAll(HomeSpecies.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Disease数据重新处理
                        if (ObjectUtils.isNotEmpty(item.getDisease()) && !"NA".equals(item.getDisease())) {
                            List<String> diseases = new ArrayList<>(Arrays.asList(item.getDisease().split(",")));
                            // 去空，去空格
                            diseases = diseases.stream().map(i -> i.trim()).filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
                            // 重新拼接保存
                            item.setDisease(diseases.stream().collect(Collectors.joining(",")));
                        }

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 基因
//                        geneService.saveGeneAndSeq(item.getDiseaseRelatedGenes(),
//                                item.getDiseaseRelatedGenes(),
//                                null,
//                                HomeSpecies.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 疾病
//                        if (ObjectUtils.isNotEmpty(item.getDisease()) && !"NA".equals(item.getDisease())) {
//                            List<String> diseases = new ArrayList<>(Arrays.asList(item.getDisease().split(",")));
//                            diseases.forEach(i -> {
//                                diseaseService.savaDiseaseAndSeq(i,
//                                        i,
//                                        null,
//                                        HomeSpecies.class.getName(),
//                                        tableName,
//                                        filePath,
//                                        file.getOriginalFilename(),
//                                        item.getId());
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
    public IPage<HomeSpecies> pageList(HomeSpeciesQuery query) {
        // 分页
        Page<HomeSpecies> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<HomeSpecies> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(HomeSpecies::getSpecies, query.getSearch())
//                    .or()
//                    .like(HomeSpecies::getDisease, query.getSearch())
//                    .or()
//                    .like(HomeSpecies::getDiseaseRelatedGenes, query.getSearch())
//                    .or()
//                    .like(HomeSpecies::getDirectEvidence, query.getSearch())
//                    .or()
//                    .like(HomeSpecies::getInferenceNetwork, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(HomeSpecies::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(HomeSpecies::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(HomeSpecies::getDisease, query.getDisease());
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
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, HomeSpeciesQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<HomeSpecies> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(HomeSpecies::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(HomeSpecies::getSpecies, query.getSpecies());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(HomeSpecies::getDisease, query.getDisease());
        }

        // 查询数据集合
        List<HomeSpecies> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<HomeSpecies> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<HomeSpeciesExport> exports = BeanConvertUtil.listConvert(list, HomeSpeciesExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Home_Species_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), HomeSpeciesExport.class)
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
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Disease related genes", "diseaseRelatedGenes");
        reader.addHeaderAlias("Direct Evidence", "directEvidence");
        reader.addHeaderAlias("Inference Network", "inferenceNetwork");
        reader.addHeaderAlias("Source", "source");
        reader.addHeaderAlias("Reference Count", "referenceCount");
        reader.addHeaderAlias("link", "link");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("diseaseRelatedGenes", "Disease related genes");
        writer.addHeaderAlias("directEvidence", "Direct Evidence");
        writer.addHeaderAlias("inferenceNetwork", "Inference Network");
        writer.addHeaderAlias("source", "Source");
        writer.addHeaderAlias("referenceCount", "Reference Count");
        writer.addHeaderAlias("link", "link");
        return writer;
    }

}

