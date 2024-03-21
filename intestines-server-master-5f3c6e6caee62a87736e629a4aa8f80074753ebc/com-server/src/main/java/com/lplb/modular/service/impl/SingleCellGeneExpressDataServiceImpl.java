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
import com.lplb.modular.mapper.SingleCellGeneExpressDataMapper;
import com.lplb.modular.model.entity.SingleCellGeneExpressData;
import com.lplb.modular.model.entity.SpatialHistology;
import com.lplb.modular.model.export.SingleCellGeneExpressDataExport;
import com.lplb.modular.model.export.SpatialHistologyExport;
import com.lplb.modular.model.query.SingleCellGeneExpressDataQuery;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataUniqueVo;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataVo;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.FiledColorService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.SingleCellGeneExpressDataService;
import com.lplb.modular.service.TissueCellLineService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表服务实现类
 */
@Service
@Transactional
public class SingleCellGeneExpressDataServiceImpl extends ServiceImpl<SingleCellGeneExpressDataMapper, SingleCellGeneExpressData> implements SingleCellGeneExpressDataService {

    private final String tableName = "in_single_cell_gene_express_data";
    private final String filePath = "/05.Single cell omics/Gene expression data";
    @Value("${file.requestPath}")
    private String fileRequestPath;
    private final String fileName = "Gene_expression_data";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private TissueCellLineService tissueCellLineService;
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
            List<SingleCellGeneExpressData> list = reader.readAll(SingleCellGeneExpressData.class);

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
                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                SingleCellGeneExpressData.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 组织细胞
//                        tissueCellLineService.savaTissueAndSeq(item.getTissue(),
//                                item.getTissue(),
//                                OmicsEnums.SINGLECELLOMICS.getId(),
//                                SingleCellGeneExpressData.class.getName(),
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
     * 查询唯一键数据列表数据
     *
     * @return
     */
    @Override
    public List<SingleCellGeneExpressDataUniqueVo> uniqueIdList() {
        LambdaQueryWrapper<SingleCellGeneExpressData> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SingleCellGeneExpressData::getUniqueId,
                SingleCellGeneExpressData::getTitle);
        wrapper.orderByAsc(SingleCellGeneExpressData::getUniqueId);
        wrapper.groupBy(SingleCellGeneExpressData::getUniqueId, SingleCellGeneExpressData::getTitle);

        List<SingleCellGeneExpressData> list = this.list(wrapper);

        // 返回结果数据集合
        List<SingleCellGeneExpressDataUniqueVo> result = new ArrayList<>();
        list.forEach(item -> {
            SingleCellGeneExpressDataUniqueVo vo = new SingleCellGeneExpressDataUniqueVo();
            vo.setUniqueId(item.getUniqueId());

            // 设置图片访问地址
            String imgUrl = fileRequestPath + "imgs/IntestineDB/05_Single_cell_omics/Gene_expression_data/"
                    + item.getUniqueId() + "/"
                    + item.getTitle() + ".png";
            vo.setImgUrl(imgUrl);
            result.add(vo);
        });


        // 返回列表
        return result;
    }

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<SingleCellGeneExpressDataVo> pageList(SingleCellGeneExpressDataQuery query) {
        // 分页
        Page<SingleCellGeneExpressData> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<SingleCellGeneExpressData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCellGeneExpressData::getUniqueId, query.getUniqueId());
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(SingleCellGeneExpressData::getGeneName, query.getKeywords())
                    .or()
                    .like(SingleCellGeneExpressData::getName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(SingleCellGeneExpressData::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SingleCellGeneExpressData::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getCancerType())) {
            wrapper.eq(SingleCellGeneExpressData::getCancerType, query.getCancerType());
        }
        if (ObjectUtils.isNotEmpty(query.getTreatmentType())) {
            wrapper.eq(SingleCellGeneExpressData::getTreatmentType, query.getTreatmentType());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;

            if ("avgLog2Fc".equals(order[0])) {
                colum = "avg_log_2_fc";
            } else if ("pct1".equals(order[0])) {
                colum = "pct_1";
            } else if ("pct2".equals(order[0])) {
                colum = "pct_2";
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

        IPage<SingleCellGeneExpressDataVo> result = BeanConvertUtil.pageConvert(page, SingleCellGeneExpressDataVo.class);
        // 循环
        result.getRecords().forEach(item -> {
            // 封装图片
            String imgUrl = fileRequestPath + "imgs/IntestineDB/05_Single_cell_omics/Gene_expression_data/"
                    + item.getUniqueId() + "/" + item.getUniqueId() + "_imgs/"
                    + item.getGeneName() + ".png";

            item.setImgUrl(imgUrl);
            // 查询颜色
            String color = filedColorService.findByType("in_single_cell_gene_express_data", "avg_log_2_fc", item.getAvgLog2Fc());
            item.setColor(color);
        });

        return result;
    }

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<SingleCellGeneExpressData> listByGeneName(String geneName) {
        LambdaQueryWrapper<SingleCellGeneExpressData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCellGeneExpressData::getGeneName, geneName);
        return this.list(wrapper);
    }

    /**
     * Cluster数据统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> clusterStatistics() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.clusterStatistics();
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
    public void exportExcel(HttpServletResponse response, SingleCellGeneExpressDataQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<SingleCellGeneExpressData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCellGeneExpressData::getUniqueId, query.getUniqueId());
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(SingleCellGeneExpressData::getGeneName, query.getKeywords())
                    .or()
                    .like(SingleCellGeneExpressData::getName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getEnsembleId())) {
            wrapper.eq(SingleCellGeneExpressData::getEnsembleId, query.getEnsembleId());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SingleCellGeneExpressData::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getCancerType())) {
            wrapper.eq(SingleCellGeneExpressData::getCancerType, query.getCancerType());
        }
        if (ObjectUtils.isNotEmpty(query.getTreatmentType())) {
            wrapper.eq(SingleCellGeneExpressData::getTreatmentType, query.getTreatmentType());
        }

        // 查询数据集合
        List<SingleCellGeneExpressData> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<SingleCellGeneExpressData> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<SingleCellGeneExpressDataExport> exports = BeanConvertUtil.listConvert(list, SingleCellGeneExpressDataExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Single cell omics_Gene expression_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), SingleCellGeneExpressDataExport.class)
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
        reader.addHeaderAlias("Name", "name");
        reader.addHeaderAlias("Cancer Type", "cancerType");
        reader.addHeaderAlias("Tissue", "tissue");
        reader.addHeaderAlias("Treatment Type", "treatmentType");
        reader.addHeaderAlias("Unique Id", "uniqueId");
        reader.addHeaderAlias("Cluster", "cluster");
        reader.addHeaderAlias("Enrichment Cell Types", "enrichmentCellTypes");
        reader.addHeaderAlias("Cell amount", "cellAmount");
        reader.addHeaderAlias("p_val", "pVal");
        reader.addHeaderAlias("avg_log2FC", "avgLog2Fc");
        reader.addHeaderAlias("pct.1", "pct1");
        reader.addHeaderAlias("pct.2", "pct2");
        reader.addHeaderAlias("p_val_adj", "pValAdj");
        reader.addHeaderAlias("Title", "title");
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
        writer.addHeaderAlias("name", "Name");
        writer.addHeaderAlias("cancerType", "Cancer Type");
        writer.addHeaderAlias("tissue", "Tissue");
        writer.addHeaderAlias("treatmentType", "Treatment Type");
        writer.addHeaderAlias("uniqueId", "Unique Id");
        writer.addHeaderAlias("cluster", "Cluster");
        writer.addHeaderAlias("enrichmentCellTypes", "Enrichment Cell Types");
        writer.addHeaderAlias("cellAmount", "Cell amount");
        writer.addHeaderAlias("pVal", "p_val");
        writer.addHeaderAlias("avgLog2Fc", "avg_log2FC");
        writer.addHeaderAlias("pct1", "pct.1");
        writer.addHeaderAlias("pct2", "pct.2");
        writer.addHeaderAlias("pValAdj", "p_val_adj");
        writer.addHeaderAlias("title", "Title");
        return writer;
    }
}

