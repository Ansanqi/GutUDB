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
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表实体类
 */
@Data
@ApiModel(value = "Probiotics（益生菌）(Probiotics)实体对象")
@TableName("in_probiotics")
public class Probiotics extends BaseEntity {

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
     * Probiotics（益生菌）
     */
    @ApiModelProperty(value = "Probiotics（益生菌）")
    @TableField(value = "probiotics")
    private String probiotics;

    /**
     * Genus（属）
     */
    @ApiModelProperty(value = "Genus（属）")
    @TableField(value = "genus")
    private String genus;

    /**
     * Location（位置）
     */
    @ApiModelProperty(value = "Location（位置）")
    @TableField(value = "location")
    private String location;

    /**
     * Function（功能）
     */
    @ApiModelProperty(value = "Function（功能）")
    @TableField(value = "function")
    private String function;

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

