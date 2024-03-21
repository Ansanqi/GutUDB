package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 10:22:14
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表实体类
 */
@Data
@ApiModel(value = "Gene expression data（基因表达数据）(GeneExpressionData)实体对象")
public class GeneExpressionDataVo implements Serializable {

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
     * 类型（Homo sapiens/Mus musculus/Other）
     */
    @ApiModelProperty(value = "类型（Homo sapiens/Mus musculus/Rattus norvegicus/Sus scrofa）")
    private String expType;

    /**
     * Peoject
     */
    @ApiModelProperty(value = "Peoject")
    private String project;

    /**
     * Body site（身体部位）
     */
    @ApiModelProperty(value = "Body site（身体部位）")
    private String bodySite;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    /**
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    private String ensembleId;

    /**
     * Mean (Case)（平均值（例））
     */
    @ApiModelProperty(value = "Mean (Case)（平均值（例））")
    private String meanCase;

    /**
     * Mean (Control)（平均值（对照标准））
     */
    @ApiModelProperty(value = "Mean (Control)（平均值（对照标准））")
    private String meanControl;

    /**
     * Log2(Fold Change)（log2（折叠变换））
     */
    @ApiModelProperty(value = "Log2(Fold Change)（log2（折叠变换））")
    private String log2FoldChange;

    /**
     * p.value（p值）
     */
    @ApiModelProperty(value = "p.value（p值）")
    private String pValue;

    @ApiModelProperty(value = "文件保存路径")
    private String filePath;

    @ApiModelProperty(value = "Boxplot")
    private String boxplotImgUrl;

    @ApiModelProperty(value = "Dotplot")
    private String dotplotImgUrl;

    @ApiModelProperty(value = "TPM Exp_img")
    private String tpmExpImg;

    @ApiModelProperty(value = "TS Score_img")
    private String tsScoreImg;

}

