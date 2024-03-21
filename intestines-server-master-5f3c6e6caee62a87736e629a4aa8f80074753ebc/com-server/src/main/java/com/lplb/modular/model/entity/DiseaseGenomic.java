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
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：疾病基因组(DiseaseGenomic)表实体类
 */
@Data
@ApiModel(value = "疾病基因组(DiseaseGenomic)实体对象")
@TableName("in_disease_genomic")
public class DiseaseGenomic extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 等级ID
     */
    @ApiModelProperty(value = "等级ID")
    @TableField(value = "level_id")
    private Long levelId;

    /**
     * 组类型（CNA_Genes/Mutated_Genes/SNP/Structural_Variant_Genes/COAD/Non-coding RNA/Metabolomics/Proteomics/Single cell omics）
     */
    @ApiModelProperty(value = "组类型（CNA_Genes/Mutated_Genes/SNP/Structural_Variant_Genes/COAD/Non-coding RNA/Metabolomics/Proteomics/Single cell omics）")
    @TableField(value = "omic_type")
    private String omicType;

    /**
     * Disease related genes（疾病相关基因）
     */
    @ApiModelProperty(value = "Disease related genes（疾病相关基因）")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Omics（组学）
     */
    @ApiModelProperty(value = "Omics（组学）")
    @TableField(value = "omics")
    private String omics;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

