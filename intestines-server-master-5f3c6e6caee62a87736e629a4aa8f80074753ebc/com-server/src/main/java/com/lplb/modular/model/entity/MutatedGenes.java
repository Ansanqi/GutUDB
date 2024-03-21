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
 * @Date（日期）： 2023-07-31 18:37:09
 * @Description（描述）：Mutated Genes（突变基因）(MutatedGenes)表实体类
 */
@Data
@ApiModel(value = "Mutated Genes（突变基因）(MutatedGenes)实体对象")
@TableName("in_mutated_genes")
public class MutatedGenes extends BaseEntity {

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
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））
     */
    @ApiModelProperty(value = "Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））")
    @TableField(value = "is_cancer_gene_source_onco_kb")
    private String isCancerGeneSourceOncoKb;

    /**
     * Freq（频率）
     */
    @ApiModelProperty(value = "Freq（频率）")
    @TableField(value = "freq")
    private String freq;

    /**
     * PMID
     */
    @ApiModelProperty(value = "PMID")
    @TableField(value = "pmid")
    private String pmid;

    /**
     * PMID跳转URL
     */
    @ApiModelProperty(value = "PMID跳转URL")
    @TableField(value = "pmid_url")
    private String pmidUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}
