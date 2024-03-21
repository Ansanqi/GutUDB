package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 17:36:50
 * @Description（描述）：CNA Genes（CAN基因）(CnaGenes)表实体类
 */
@Data
@ApiModel(value = "CNA Genes（CAN基因）(CnaGenes)实体对象")
public class CnaGenesVo extends BaseEntity {

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
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    /**
     * Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））
     */
    @ApiModelProperty(value = "Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））")
    private String isCancerGeneSourceOncoKb;

    /**
     * Cytoband（细胞带）
     */
    @ApiModelProperty(value = "Cytoband（细胞带）")
    private String cytoband;

    /**
     * CAN
     */
    @ApiModelProperty(value = "CAN")
    private String can;

    /**
     * Freq（频率）
     */
    @ApiModelProperty(value = "Freq（频率）")
    private String freq;

    /**
     * PMID
     */
    @ApiModelProperty(value = "PMID")
    private String pmid;

    /**
     * PMID跳转地址
     */
    @ApiModelProperty(value = "PMID跳转地址")
    private String pmidUrl;

    @ApiModelProperty(value = "颜色")
    private String color;

}

