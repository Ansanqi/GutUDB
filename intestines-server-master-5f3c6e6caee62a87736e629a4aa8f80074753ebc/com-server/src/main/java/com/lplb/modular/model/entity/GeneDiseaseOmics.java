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
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表实体类
 */
@Data
@ApiModel(value = "Gene_disease_omics(GeneDiseaseOmics)实体对象")
@TableName("in_gene_disease_omics")
public class GeneDiseaseOmics extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Disease related genes
     */
    @ApiModelProperty(value = "Disease related genes")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Disease
     */
    @ApiModelProperty(value = "Disease")
    @TableField(value = "disease")
    private String disease;

    /**
     * Omics
     */
    @ApiModelProperty(value = "Omics")
    @TableField(value = "omics")
    private String omics;

    /**
     * Source_url
     */
    @ApiModelProperty(value = "sources")
    @TableField(value = "sources")
    private String sources;

    @ApiModelProperty(value = "url")
    @TableField(value = "url")
    private String url;

    /**
     * Type
     */
    @ApiModelProperty(value = "Type")
    @TableField(value = "typess")
    private String typess;

    /**
     * categories
     */
    @ApiModelProperty(value = "Omics level")
    @TableField(value = "omics_level")
    private String omicsLevel;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

