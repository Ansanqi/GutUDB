package com.lplb.modular.model.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表实体类
 */
@Data
@ApiModel(value = "单细胞组学基因表达数据详情(SingleCellGeneExpressData)实体对象")
@TableName("in_single_cell_gene_express_data")
public class SingleCellGeneExpressData extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    @TableField(value = "omics_id")
    private Long omicsId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Name（名称）
     */
    @ApiModelProperty(value = "Name（名称）")
    @TableField(value = "name")
    private String name;

    /**
     * Cancer Type（癌症类型）
     */
    @ApiModelProperty(value = "Cancer Type（癌症类型）")
    @TableField(value = "cancer_type")
    private String cancerType;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    @TableField(value = "tissue")
    private String tissue;

    /**
     * Treatment Type（处理类型）
     */
    @ApiModelProperty(value = "Treatment Type（处理类型）")
    @TableField(value = "treatment_type")
    private String treatmentType;

    /**
     * Unique Id（唯一ID）
     */
    @ApiModelProperty(value = "Unique Id（唯一ID）")
    @TableField(value = "unique_id")
    private String uniqueId;

    /**
     * Cluster（簇）
     */
    @ApiModelProperty(value = "Cluster（簇）")
    @TableField(value = "cluster")
    private String cluster;

    /**
     * Enrichment Cell Types（富集细胞类型）
     */
    @ApiModelProperty(value = "Enrichment Cell Types（富集细胞类型）")
    @TableField(value = "enrichment_cell_types")
    private String enrichmentCellTypes;

    /**
     * Cell amount（细胞数量）
     */
    @ApiModelProperty(value = "Cell amount（细胞数量）")
    @TableField(value = "cell_amount")
    private String cellAmount;

    /**
     * p_val（p值）
     */
    @ApiModelProperty(value = "p_val（p值）")
    @TableField(value = "p_val")
    private String pVal;

    /**
     * avg_log2FC（log2FC平均值）
     */
    @ApiModelProperty(value = "avg_log2FC（log2FC平均值）")
    @TableField(value = "avg_log_2_fc")
    private String avgLog2Fc;

    /**
     * pct.1（百分数1）
     */
    @ApiModelProperty(value = "pct.1（百分数1）")
    @TableField(value = "pct_1")
    private BigDecimal pct1;

    /**
     * pct.2（百分数2）
     */
    @ApiModelProperty(value = "pct.2（百分数2）")
    @TableField(value = "pct_2")
    private BigDecimal pct2;

    /**
     * p_val_adj（可调整p值）
     */
    @ApiModelProperty(value = "p_val_adj（可调整p值）")
    @TableField(value = "p_val_adj")
    private BigDecimal pValAdj;

    /**
     * Title（标题）
     */
    @ApiModelProperty(value = "Title（标题）")
    @TableField(value = "title")
    private String title;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

