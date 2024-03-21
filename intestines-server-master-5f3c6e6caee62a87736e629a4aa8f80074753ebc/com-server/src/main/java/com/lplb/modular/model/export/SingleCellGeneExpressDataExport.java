package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表实体类
 */
@Data
public class SingleCellGeneExpressDataExport {

    /**
     * Ensemble ID（整体ID）
     */
    @ExcelProperty("Ensemble ID")
    private String ensembleId;

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Name（名称）
     */
    @ExcelProperty("Name")
    private String name;

    /**
     * Cancer Type（癌症类型）
     */
    @ExcelProperty("Cancer Type")
    private String cancerType;

    /**
     * Tissue（组织）
     */
    @ExcelProperty("Tissue")
    private String tissue;

    /**
     * Treatment Type（处理类型）
     */
    @ExcelProperty("Treatment Type")
    private String treatmentType;

    /**
     * Unique Id（唯一ID）
     */
    @ExcelProperty("Unique Id")
    private String uniqueId;

    /**
     * Cluster（簇）
     */
    @ExcelProperty("Cluster")
    private String cluster;

    /**
     * Enrichment Cell Types（富集细胞类型）
     */
    @ExcelProperty("Enrichment Cell Types")
    private String enrichmentCellTypes;

    /**
     * Cell amount（细胞数量）
     */
    @ExcelProperty("Cell amount")
    private String cellAmount;

    /**
     * p_val（p值）
     */
    @ExcelProperty("p_val")
    private String pVal;

    /**
     * avg_log2FC（log2FC平均值）
     */
    @ExcelProperty("avg_log2FC")
    private String avgLog2Fc;

    /**
     * pct.1（百分数1）
     */
    @ExcelProperty("pct.1")
    private BigDecimal pct1;

    /**
     * pct.2（百分数2）
     */
    @ExcelProperty("pct.2")
    private BigDecimal pct2;

    /**
     * p_val_adj（可调整p值）
     */
    @ExcelProperty("p_val_adj")
    private BigDecimal pValAdj;

    /**
     * Title（标题）
     */
    @ExcelProperty("Title")
    private String title;

}

