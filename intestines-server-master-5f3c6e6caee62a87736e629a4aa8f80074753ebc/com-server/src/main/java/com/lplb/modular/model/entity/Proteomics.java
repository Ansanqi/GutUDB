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
 * @Description（描述）：Proteomics（蛋白质组学）(Proteomics)表实体类
 */
@Data
@ApiModel(value = "Proteomics（蛋白质组学）(Proteomics)实体对象")
@TableName("in_proteomics")
public class Proteomics extends BaseEntity {

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
     * Sapiens（智人）
     */
    @ApiModelProperty(value = "Sapiens（智人）")
    @TableField(value = "sapiens")
    private String sapiens;

    /**
     * Position（位置）
     */
    @ApiModelProperty(value = "Position（位置）")
    @TableField(value = "position")
    private String position;

    /**
     * Protein Name（蛋白质名称）
     */
    @ApiModelProperty(value = "Protein Name（蛋白质名称）")
    @TableField(value = "protein_name")
    private String proteinName;

    /**
     * Induction（诱因）
     */
    @ApiModelProperty(value = "Induction（诱因）")
    @TableField(value = "induction")
    private String induction;

    /**
     * Length（长度）
     */
    @ApiModelProperty(value = "Length（长度）")
    @TableField(value = "length")
    private Integer length;

    /**
     * Post Translational Modification（翻译后修饰）
     */
    @ApiModelProperty(value = "Post Translational Modification（翻译后修饰）")
    @TableField(value = "post_translational_modification")
    private String postTranslationalModification;

    /**
     * Function（功能）
     */
    @ApiModelProperty(value = "Function（功能）")
    @TableField(value = "function")
    private String function;

    /**
     * Activity Regulation（活动调节）
     */
    @ApiModelProperty(value = "Activity Regulation（活动调节）")
    @TableField(value = "activity_regulation")
    private String activityRegulation;

    /**
     * Pathway（途径）
     */
    @ApiModelProperty(value = "Pathway（途径）")
    @TableField(value = "pathway")
    private String pathway;

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

