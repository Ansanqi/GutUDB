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
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.A3ssMatsJeceMapper;
import com.lplb.modular.model.entity.A3ssMatsJece;
import com.lplb.modular.model.entity.GeoInfo;
import com.lplb.modular.model.export.A3ssMatsJeceExport;
import com.lplb.modular.model.export.GeoInfoExport;
import com.lplb.modular.model.query.A3ssMatsJeceQuery;
import com.lplb.modular.service.A3ssMatsJeceService;
import com.lplb.modular.service.GeneService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A3SS.MATS.JCEC(A3ssMatsJece)表服务实现类
 */
@Service
@Transactional
public class A3ssMatsJeceServiceImpl extends ServiceImpl<A3ssMatsJeceMapper, A3ssMatsJece> implements A3ssMatsJeceService {

    private final String tableName = "in_a3ss_mats_jece";
    private final String filePath = "/03.Transcriptomic/Alternative Splicing";
    private final String fileName = "A3SS_MATS_JCEC";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;

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
            List<A3ssMatsJece> list = reader.readAll(A3ssMatsJece.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置levelName
                        List<String> level1List = new ArrayList<>(Arrays.asList(item.getIncLevel1Name().split("-"))).stream().filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
                        item.setIncLevel1Name(level1List.stream().collect(Collectors.joining(",")));
                        List<String> level2List = new ArrayList<>(Arrays.asList(item.getIncLevel2Name().split("-"))).stream().filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
                        item.setIncLevel2Name(level2List.stream().collect(Collectors.joining(",")));

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 基因
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                A3ssMatsJece.class.getName(),
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
    public IPage<A3ssMatsJece> pageList(A3ssMatsJeceQuery query) {
        // 分页
        Page<A3ssMatsJece> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<A3ssMatsJece> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(A3ssMatsJece::getDataAccessId, query.getDataAccessId());
        wrapper.eq(A3ssMatsJece::getFileNo, query.getFile());
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(A3ssMatsJece::getGeneName, query.getSearch())
//                    .or()
//                    .like(A3ssMatsJece::getChr, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(A3ssMatsJece::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(A3ssMatsJece::getEnsembleId, query.getEnsembleId());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;

            if ("longExonStart0Base".equals(order[0])) {
                colum = "long_exon_start_0_base";
            } else if ("ijcSample1".equals(order[0])) {
                colum = "ijc_sample_1";
            } else if ("sjcSample1".equals(order[0])) {
                colum = "sjc_sample_1";
            } else if ("ijcSample2".equals(order[0])) {
                colum = "ijc_sample_2";
            } else if ("sjcSample2".equals(order[0])) {
                colum = "sjc_sample_2";
            } else if ("incLevel1".equals(order[0])) {
                colum = "inc_level_1";
            } else if ("incLevel2".equals(order[0])) {
                colum = "inc_level_2";
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
    public void exportExcel(HttpServletResponse response, A3ssMatsJeceQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<A3ssMatsJece> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(A3ssMatsJece::getDataAccessId, query.getDataAccessId());
        wrapper.eq(A3ssMatsJece::getFileNo, query.getFile());
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(A3ssMatsJece::getGeneName, query.getKeywords());
        }

        // 查询数据集合
        List<A3ssMatsJece> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<A3ssMatsJece> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<A3ssMatsJeceExport> exports = BeanConvertUtil.listConvert(list, A3ssMatsJeceExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_Alternative Splicing_Data Access_A3SS.MATS.JCEC_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), A3ssMatsJeceExport.class)
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
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("chr", "chr");
        reader.addHeaderAlias("strand", "strand");
        reader.addHeaderAlias("longExonStart_0base", "longExonStart0Base");
        reader.addHeaderAlias("longExonEnd", "longExonEnd");
        reader.addHeaderAlias("shortES", "shortEs");
        reader.addHeaderAlias("shortEE", "shortEe");
        reader.addHeaderAlias("flankingES", "flankingEs");
        reader.addHeaderAlias("flankingEE", "flankingEe");
        reader.addHeaderAlias("ID", "idNo");
        reader.addHeaderAlias("IJC_SAMPLE_1", "ijcSample1");
        reader.addHeaderAlias("SJC_SAMPLE_1", "sjcSample1");
        reader.addHeaderAlias("IJC_SAMPLE_2", "ijcSample2");
        reader.addHeaderAlias("SJC_SAMPLE_2", "sjcSample2");
        reader.addHeaderAlias("IncFormLen", "incFormLen");
        reader.addHeaderAlias("SkipFormLen", "skipFormLen");
        reader.addHeaderAlias("PValue", "pValue");
        reader.addHeaderAlias("FDR", "fdr");
        reader.addHeaderAlias("IncLevel1", "incLevel1");
        reader.addHeaderAlias("IncLevel2", "incLevel2");
        reader.addHeaderAlias("IncLevelDifference", "incLevelDifference");

        reader.addHeaderAlias("Data Access ID", "dataAccessId");
        reader.addHeaderAlias("File", "fileNo");
        reader.addHeaderAlias("IncLevel1Name", "incLevel1Name");
        reader.addHeaderAlias("IncLevel2Name", "incLevel2Name");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("ensembleId", "Ensemble ID");
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("chr", "chr");
        writer.addHeaderAlias("strand", "strand");
        writer.addHeaderAlias("longExonStart0Base", "longExonStart_0base");
        writer.addHeaderAlias("longExonEnd", "longExonEnd");
        writer.addHeaderAlias("shortEs", "shortES");
        writer.addHeaderAlias("shortEe", "shortEE");
        writer.addHeaderAlias("flankingEs", "flankingES");
        writer.addHeaderAlias("flankingEe", "flankingEE");
        writer.addHeaderAlias("idNo", "ID");
        writer.addHeaderAlias("ijcSample1", "IJC_SAMPLE_1");
        writer.addHeaderAlias("sjcSample1", "SJC_SAMPLE_1");
        writer.addHeaderAlias("ijcSample2", "IJC_SAMPLE_2");
        writer.addHeaderAlias("sjcSample2", "SJC_SAMPLE_2");
        writer.addHeaderAlias("incFormLen", "IncFormLen");
        writer.addHeaderAlias("skipFormLen", "SkipFormLen");
        writer.addHeaderAlias("pValue", "PValue");
        writer.addHeaderAlias("fdr", "FDR");
        writer.addHeaderAlias("incLevel1", "IncLevel1");
        writer.addHeaderAlias("incLevel2", "IncLevel2");
        writer.addHeaderAlias("incLevelDifference", "IncLevelDifference");

        writer.addHeaderAlias("dataAccessId", "Data Access ID");
        writer.addHeaderAlias("fileNo", "File");
        writer.addHeaderAlias("incLevel1Name", "IncLevel1Name");
        writer.addHeaderAlias("incLevel2Name", "IncLevel2Name");
        return writer;
    }
}
