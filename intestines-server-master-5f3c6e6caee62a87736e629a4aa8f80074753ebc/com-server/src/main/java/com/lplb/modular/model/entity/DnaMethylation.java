package com.lplb.modular.model.entity;

import java.math.BigDecimal;
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
 * @Date（日期）： 2023-07-29 18:31:44
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)表实体类
 */
@Data
@ApiModel(value = "DNA methylation（DNA甲基化）(DnaMethylation)实体对象")
@TableName("in_dna_methylation")
public class DnaMethylation extends BaseEntity {

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
     * Ensemble ID
     */
    @ApiModelProperty(value = "Ensemble ID")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Gene Name
     */
    @ApiModelProperty(value = "Gene Name")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Chromosome
     */
    @ApiModelProperty(value = "Chromosome")
    @TableField(value = "chromosome")
    private String chromosome;

    /**
     * Start Position
     */
    @ApiModelProperty(value = "Start Position")
    @TableField(value = "start_position")
    private Long startPosition;

    /**
     * End Position
     */
    @ApiModelProperty(value = "End Position")
    @TableField(value = "end_position")
    private Long endPosition;

    /**
     * CpG Length
     */
    @ApiModelProperty(value = "CpG Length")
    @TableField(value = "cpg_length")
    private Integer cpgLength;

    /**
     * CpG Number（编号）
     */
    @ApiModelProperty(value = "CpG Number（编号）")
    @TableField(value = "cpg_number")
    private Long cpgNumber;

    /**
     * Average Methylation Level（平均甲基化水平）
     */
    @ApiModelProperty(value = "Average Methylation Level（平均甲基化水平）")
    @TableField(value = "average_methylation_level")
    private BigDecimal averageMethylationLevel;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    @TableField(value = "strand")
    private String strand;

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
     * Species（物种）
     */
    @ApiModelProperty(value = "Species（物种）")
    @TableField(value = "species")
    private String species;

    /**
     * Genomics Location（基因组学位置）
     */
    @ApiModelProperty(value = "Genomics Location（基因组学位置）")
    @TableField(value = "genomics_location")
    private String genomicsLocation;

    /**
     * Sample Name（样品名称）
     */
    @ApiModelProperty(value = "Sample Name（样品名称）")
    @TableField(value = "sample_name")
    private String sampleName;

    /**
     * Description（描述）
     */
    @ApiModelProperty(value = "Description（描述）")
    @TableField(value = "description")
    private String description;

    /**
     * Tissue/Cell Line（组织/细胞系)
     */
    @ApiModelProperty(value = "Tissue/Cell Line（组织/细胞系)")
    @TableField(value = "tissue_cell_line")
    private String tissueCellLine;

    /**
     * Gender（Male）
     */
    @ApiModelProperty(value = "Gender（Male）")
    @TableField(value = "gender")
    private String gender;

    /**
     * Age（年龄）
     */
    @ApiModelProperty(value = "Age（年龄）")
    @TableField(value = "age")
    private String age;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Platform（分析仪）
     */
    @ApiModelProperty(value = "Platform（分析仪）")
    @TableField(value = "platform")
    private String platform;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

