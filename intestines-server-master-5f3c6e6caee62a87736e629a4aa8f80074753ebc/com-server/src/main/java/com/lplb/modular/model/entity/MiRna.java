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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：miRNA（微型核糖核酸）(MiRna)表实体类
 */
@Data
@ApiModel(value = "miRNA（微型核糖核酸）(MiRna)实体对象")
@TableName("in_mi_rna")
public class MiRna extends BaseEntity {

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
     * Category（类别）
     */
    @ApiModelProperty(value = "Category（类别）")
    @TableField(value = "categories")
    private String categories;

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
     * Expression pattern of miRNA（miRNA的表达模式）
     */
    @ApiModelProperty(value = "Expression pattern of miRNA（miRNA的表达模式）")
    @TableField(value = "expression_pattern_of_mi_rna")
    private String expressionPatternOfMiRna;

    /**
     * Detection method for miRNA expression（miRNA表达检测方法）
     */
    @ApiModelProperty(value = "Detection method for miRNA expression（miRNA表达检测方法）")
    @TableField(value = "detection_method_for_mi_rna_expression")
    private String detectionMethodForMiRnaExpression;

    /**
     * Title（标题）
     */
    @ApiModelProperty(value = "Title（标题）")
    @TableField(value = "title")
    private String title;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

