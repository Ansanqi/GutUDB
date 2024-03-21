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
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：Spatial histology(SpatialHistology)表实体类
 */
@Data
@ApiModel(value = "Spatial histology(SpatialHistology)实体对象")
@TableName("in_spatial_histology")
public class SpatialHistology extends BaseEntity {

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
     * Species（物种）
     */
    @ApiModelProperty(value = "Species（物种）")
    @TableField(value = "species")
    private String species;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    @TableField(value = "tissue")
    private String tissue;

    /**
     * Biotech Categories（生物技术类别）
     */
    @ApiModelProperty(value = "Biotech Categories（生物技术类别）")
    @TableField(value = "biotech_categories")
    private String biotechCategories;

    /**
     * Expression Range（表达式范围）
     */
    @ApiModelProperty(value = "Expression Range（表达式范围）")
    @TableField(value = "expression_range")
    private String expressionRange;

    /**
     * Biotech（生物技术）
     */
    @ApiModelProperty(value = "Biotech（生物技术）")
    @TableField(value = "biotech")
    private String biotech;

    /**
     * N Unit
     */
    @ApiModelProperty(value = "N Unit")
    @TableField(value = "n_unit")
    private String nUnit;

    /**
     * Title（标题）
     */
    @ApiModelProperty(value = "Title（标题）")
    @TableField(value = "title")
    private String title;

    /**
     * Project（项目）
     */
    @ApiModelProperty(value = "Project（项目）")
    @TableField(value = "project")
    private String project;

    /**
     * 项目跳转地址
     */
    @ApiModelProperty(value = "项目跳转地址")
    @TableField(value = "project_url")
    private String projectUrl;

    /**
     * Sample ID（样本ID）
     */
    @ApiModelProperty(value = "Sample ID（样本ID）")
    @TableField(value = "sample_id")
    private String sampleId;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

