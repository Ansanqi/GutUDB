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
 * @Description（描述）：SNP(Snp)表实体类
 */
@Data
@ApiModel(value = "SNP(Snp)实体对象")
@TableName("in_snp")
public class Snp extends BaseEntity {

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
     * Name（名称）
     */
    @ApiModelProperty(value = "Name（名称）")
    @TableField(value = "name")
    private String name;

    /**
     * GRCh38Chromosome（GRCh38染色体）
     */
    @ApiModelProperty(value = "GRCh38Chromosome（GRCh38染色体）")
    @TableField(value = "grch_38_chromosome")
    private String grch38Chromosome;

    /**
     * GRCh38 Location（GRCh38位置）
     */
    @ApiModelProperty(value = "GRCh38 Location（GRCh38位置）")
    @TableField(value = "grch_38_location")
    private String grch38Location;

    /**
     * Condition（环境）
     */
    @ApiModelProperty(value = "Condition（环境）")
    @TableField(value = "conditions")
    private String conditions;

    /**
     * Clinical Significance（临床显着性）
     */
    @ApiModelProperty(value = "Clinical Significance（临床显着性）")
    @TableField(value = "clinical_significance")
    private String clinicalSignificance;

    /**
     * Review Status（审查状态）
     */
    @ApiModelProperty(value = "Review Status（审查状态）")
    @TableField(value = "review_status")
    private String reviewStatus;

    /**
     * Protein Change（蛋白质变化）
     */
    @ApiModelProperty(value = "Protein Change（蛋白质变化）")
    @TableField(value = "protein_change")
    private String proteinChange;

    /**
     * Accession（书目）
     */
    @ApiModelProperty(value = "Accession（书目）")
    @TableField(value = "accession")
    private String accession;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

