package com.lplb.modular.model.vo;

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
public class SpatialHistologyVo extends BaseEntity {

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
     * Species（物种）
     */
    @ApiModelProperty(value = "Species（物种）")
    private String species;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    /**
     * Biotech Categories（生物技术类别）
     */
    @ApiModelProperty(value = "Biotech Categories（生物技术类别）")
    private String biotechCategories;

    /**
     * Expression Range（表达式范围）
     */
    @ApiModelProperty(value = "Expression Range（表达式范围）")
    private String expressionRange;

    /**
     * Biotech（生物技术）
     */
    @ApiModelProperty(value = "Biotech（生物技术）")
    private String biotech;

    /**
     * N Unit
     */
    @ApiModelProperty(value = "N Unit")
    private String nUnit;

    /**
     * Title（标题）
     */
    @ApiModelProperty(value = "Title（标题）")
    private String title;

    /**
     * Project（项目）
     */
    @ApiModelProperty(value = "Project（项目）")
    private String project;

    /**
     * 项目跳转地址
     */
    @ApiModelProperty(value = "项目跳转地址")
    private String projectUrl;

    /**
     * Sample ID（样本ID）
     */
    @ApiModelProperty(value = "Sample ID（样本ID）")
    private String sampleId;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "Annotation图片访问地址")
    private String annotationImg;

    @ApiModelProperty(value = "Expression图片访问地址")
    private String expressionImg;

}

