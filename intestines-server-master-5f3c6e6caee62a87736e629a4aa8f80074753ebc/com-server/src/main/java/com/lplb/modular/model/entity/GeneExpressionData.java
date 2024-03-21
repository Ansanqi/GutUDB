package com.lplb.modular.model.entity;

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
 * @Date（日期）： 2023-08-04 10:22:14
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表实体类
 */
@Data
@ApiModel(value = "Gene expression data（基因表达数据）(GeneExpressionData)实体对象")
@TableName("in_gene_expression_data")
public class GeneExpressionData extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
     * 类型（Homo sapiens/Mus musculus/Other）
     */
    @ApiModelProperty(value = "类型（Homo sapiens/Mus musculus/Rattus norvegicus/Sus scrofa）")
    @TableField(value = "exp_type")
    private String expType;

    /**
     * Peoject
     */
    @ApiModelProperty(value = "Peoject")
    @TableField(value = "project")
    private String project;

    /**
     * Body site（身体部位）
     */
    @ApiModelProperty(value = "Body site（身体部位）")
    @TableField(value = "body_site")
    private String bodySite;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Mean (Case)（平均值（例））
     */
    @ApiModelProperty(value = "Mean (Case)（平均值（例））")
    @TableField(value = "mean_case")
    private String meanCase;

    /**
     * Mean (Control)（平均值（对照标准））
     */
    @ApiModelProperty(value = "Mean (Control)（平均值（对照标准））")
    @TableField(value = "mean_control")
    private String meanControl;

    /**
     * Log2(Fold Change)（log2（折叠变换））
     */
    @ApiModelProperty(value = "Log2(Fold Change)（log2（折叠变换））")
    @TableField(value = "log_2_fold_change")
    private String log2FoldChange;

    /**
     * p.value（p值）
     */
    @ApiModelProperty(value = "p.value（p值）")
    @TableField(value = "p_value")
    private String pValue;

    @ApiModelProperty(value = "文件路径保存地址")
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

