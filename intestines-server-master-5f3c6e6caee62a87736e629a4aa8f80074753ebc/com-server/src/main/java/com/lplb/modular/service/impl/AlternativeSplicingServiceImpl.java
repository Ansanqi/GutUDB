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
import com.lplb.modular.mapper.AlternativeSplicingMapper;
import com.lplb.modular.model.entity.AlternativeSplicing;
import com.lplb.modular.model.entity.ApaPametaMerge;
import com.lplb.modular.model.export.AlternativeSplicingExport;
import com.lplb.modular.model.export.ApaPametaMergeExport;
import com.lplb.modular.model.query.AlternativeSplicingQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.AlternativeSplicingService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表服务实现类
 */
@Service
@Transactional
public class AlternativeSplicingServiceImpl extends ServiceImpl<AlternativeSplicingMapper, AlternativeSplicing> implements AlternativeSplicingService {

    private final String tableName = "in_alternative_splicing";
    private final String filePath = "/05.Single cell omics/Single-cell alternative splicing";
    private final String fileName = "Alternative_splicing";

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
            List<AlternativeSplicing> list = reader.readAll(AlternativeSplicing.class);

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
//                        // 基因
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                AlternativeSplicing.class.getName(),
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
    public IPage<AlternativeSplicing> pageList(AlternativeSplicingQuery query) {
        // 分页
        Page<AlternativeSplicing> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<AlternativeSplicing> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(AlternativeSplicing::getGeneName, query.getSearch())
//                    .or()
//                    .like(AlternativeSplicing::getCluster1, query.getSearch())
//                    .or()
//                    .like(AlternativeSplicing::getCluster2, query.getSearch())
//                    .or()
//                    .like(AlternativeSplicing::getJunction, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(AlternativeSplicing::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getCluster1())) {
            wrapper.eq(AlternativeSplicing::getCluster1, query.getCluster1());
        }
        if (ObjectUtils.isNotEmpty(query.getCluster2())) {
            wrapper.eq(AlternativeSplicing::getCluster2, query.getCluster2());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;

            if ("log10QValue".equals(order[0])) {
                colum = "log_10_q_value";
            } else if ("log10PValue".equals(order[0])) {
                colum = "log_10_p_value";
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
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<AlternativeSplicing> listByGeneName(String geneName) {
        LambdaQueryWrapper<AlternativeSplicing> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AlternativeSplicing::getGeneName, geneName);
        return this.list(wrapper);
    }

    /**
     * Top 10 Genes for Single-cell Alternative Splicing
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10GenesForSingleCellAlternativeSplicing() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10GenesForSingleCellAlternativeSplicing();
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
    public void exportExcel(HttpServletResponse response, AlternativeSplicingQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<AlternativeSplicing> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(AlternativeSplicing::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getCluster1())) {
            wrapper.eq(AlternativeSplicing::getCluster1, query.getCluster1());
        }
        if (ObjectUtils.isNotEmpty(query.getCluster2())) {
            wrapper.eq(AlternativeSplicing::getCluster2, query.getCluster2());
        }

        // 查询数据集合
        List<AlternativeSplicing> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<AlternativeSplicing> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<AlternativeSplicingExport> exports = BeanConvertUtil.listConvert(list, AlternativeSplicingExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Single cell omics_Alternative splicing_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), AlternativeSplicingExport.class)
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
        reader.addHeaderAlias("Cluster1", "cluster1");
        reader.addHeaderAlias("Cluster2", "cluster2");
        reader.addHeaderAlias("Junction", "junction");
        reader.addHeaderAlias("logFC", "logFc");
        reader.addHeaderAlias("adj.P.Val", "adjPVal");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("cluster1", "Cluster1");
        writer.addHeaderAlias("cluster2", "Cluster2");
        writer.addHeaderAlias("junction", "Junction");
        writer.addHeaderAlias("logFc", "logFC");
        writer.addHeaderAlias("adjPVal", "adj.P.Val");
        return writer;
    }

}

