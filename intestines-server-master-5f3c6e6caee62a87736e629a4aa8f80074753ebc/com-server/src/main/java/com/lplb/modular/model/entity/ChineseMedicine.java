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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表实体类
 */
@Data
@ApiModel(value = "Chinese Medicine（中药）(ChineseMedicine)实体对象")
@TableName("in_chinese_medicine")
public class ChineseMedicine extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Disease related genes（疾病相关基因）
     */
    @ApiModelProperty(value = "Disease related genes（疾病相关基因）")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Herbs Associated with This Disease（与本病相关的草药）
     */
    @ApiModelProperty(value = "Herbs Associated with This Disease（与本病相关的草药）")
    @TableField(value = "herbs_associated_with_this_disease")
    private String herbsAssociatedWithThisDisease;

    /**
     * herbs_associated_with_this_disease_url
     */
    @ApiModelProperty(value = "herbs_associated_with_this_disease_url")
    @TableField(value = "herbs_associated_with_this_disease_url")
    private String herbsAssociatedWithThisDiseaseUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

