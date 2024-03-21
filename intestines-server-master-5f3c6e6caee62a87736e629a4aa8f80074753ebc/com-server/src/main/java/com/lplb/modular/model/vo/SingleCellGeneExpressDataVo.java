package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表实体类
 */
@Data
@ApiModel(value = "单细胞组学基因表达数据详情(SingleCellGeneExpressData)实体对象")
public class SingleCellGeneExpressDataVo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    private Long omicsId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    /**
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    private String ensembleId;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    /**
     * Name（名称）
     */
    @ApiModelProperty(value = "Name（名称）")
    private String name;

    /**
     * Cancer Type（癌症类型）
     */
    @ApiModelProperty(value = "Cancer Type（癌症类型）")
    private String cancerType;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    /**
     * Treatment Type（处理类型）
     */
    @ApiModelProperty(value = "Treatment Type（处理类型）")
    private String treatmentType;

    /**
     * Unique Id（唯一ID）
     */
    @ApiModelProperty(value = "Unique Id（唯一ID）")
    private String uniqueId;

    /**
     * Cluster（簇）
     */
    @ApiModelProperty(value = "Cluster（簇）")
    private String cluster;

    /**
     * Enrichment Cell Types（富集细胞类型）
     */
    @ApiModelProperty(value = "Enrichment Cell Types（富集细胞类型）")
    private String enrichmentCellTypes;

    /**
     * Cell amount（细胞数量）
     */
    @ApiModelProperty(value = "Cell amount（细胞数量）")
    private String cellAmount;

    /**
     * p_val（p值）
     */
    @ApiModelProperty(value = "p_val（p值）")
    private String pVal;

    /**
     * avg_log2FC（log2FC平均值）
     */
    @ApiModelProperty(value = "avg_log2FC（log2FC平均值）")
    private String avgLog2Fc;

    /**
     * pct.1（百分数1）
     */
    @ApiModelProperty(value = "pct.1（百分数1）")
    private BigDecimal pct1;

    /**
     * pct.2（百分数2）
     */
    @ApiModelProperty(value = "pct.2（百分数2）")
    private BigDecimal pct2;

    /**
     * p_val_adj（可调整p值）
     */
    @ApiModelProperty(value = "p_val_adj（可调整p值）")
    private BigDecimal pValAdj;

    /**
     * Title（标题）
     */
    @ApiModelProperty(value = "Title（标题）")
    private String title;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "颜色")
    private String color;

}

