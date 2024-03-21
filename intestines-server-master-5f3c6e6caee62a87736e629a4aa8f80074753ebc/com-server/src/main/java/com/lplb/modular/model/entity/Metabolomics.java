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
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表实体类
 */
@Data
@ApiModel(value = "Metabolomics（代谢组学）(Metabolomics)实体对象")
@TableName("in_metabolomics")
public class Metabolomics extends BaseEntity {

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
     * Gut Microbiota（肠道菌群）
     */
    @ApiModelProperty(value = "Gut Microbiota（肠道菌群）")
    @TableField(value = "gut_microbiota")
    private String gutMicrobiota;

    /**
     * Metabolite（代谢物）
     */
    @ApiModelProperty(value = "Metabolite（代谢物）")
    @TableField(value = "metabolite")
    private String metabolite;

    /**
     * species
     */
    @ApiModelProperty(value = "species")
    @TableField(value = "species")
    private String species;

    /**
     * Classification（分类）
     */
    @ApiModelProperty(value = "Classification（分类）")
    @TableField(value = "classification")
    private String classification;

    /**
     * Description（描述）
     */
    @ApiModelProperty(value = "Description（描述）")
    @TableField(value = "description")
    private String description;

    /**
     * Sample Type（样本类型）
     */
    @ApiModelProperty(value = "Sample Type（样本类型）")
    @TableField(value = "sample_type")
    private String sampleType;

    /**
     * Substrate（基质）
     */
    @ApiModelProperty(value = "Substrate（基质）")
    @TableField(value = "substrate")
    private String substrate;

    /**
     * Alteration（变更）
     */
    @ApiModelProperty(value = "Alteration（变更）")
    @TableField(value = "alteration")
    private String alteration;

    /**
     * Experimental Method（实验方法）
     */
    @ApiModelProperty(value = "Experimental Method（实验方法）")
    @TableField(value = "experimental_method")
    private String experimentalMethod;

    /**
     * Measurement Technique（测量技术）
     */
    @ApiModelProperty(value = "Measurement Technique（测量技术）")
    @TableField(value = "measurement_technique")
    private String measurementTechnique;

    /**
     * Gene Association（基因关联）
     */
    @ApiModelProperty(value = "Gene Association（基因关联）")
    @TableField(value = "gene_association")
    private String geneAssociation;

    @ApiModelProperty(value = "PMID")
    @TableField(value = "pmid")
    private String pmid;

    @ApiModelProperty(value = "PMID Association")
    @TableField(value = "pmid_association")
    private String pmidAssociation;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

