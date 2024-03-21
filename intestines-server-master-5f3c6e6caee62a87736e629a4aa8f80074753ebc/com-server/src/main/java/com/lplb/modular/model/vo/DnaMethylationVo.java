package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:31:44
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)表实体类
 */
@Data
@ApiModel(value = "DNA methylation（DNA甲基化）(DnaMethylation)实体对象")
public class DnaMethylationVo extends BaseEntity {

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
     * Ensemble ID
     */
    @ApiModelProperty(value = "Ensemble ID")
    private String ensembleId;

    /**
     * Gene Name
     */
    @ApiModelProperty(value = "Gene Name")
    private String geneName;

    /**
     * Chromosome
     */
    @ApiModelProperty(value = "Chromosome")
    private String chromosome;

    /**
     * Start Position
     */
    @ApiModelProperty(value = "Start Position")
    private Long startPosition;

    /**
     * End Position
     */
    @ApiModelProperty(value = "End Position")
    private Long endPosition;

    /**
     * CpG Length
     */
    @ApiModelProperty(value = "CpG Length")
    private Integer cpgLength;

    /**
     * CpG Number（编号）
     */
    @ApiModelProperty(value = "CpG Number（编号）")
    private Long cpgNumber;

    /**
     * Average Methylation Level（平均甲基化水平）
     */
    @ApiModelProperty(value = "Average Methylation Level（平均甲基化水平）")
    private BigDecimal averageMethylationLevel;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    private String strand;

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
     * Species（物种）
     */
    @ApiModelProperty(value = "Species（物种）")
    private String species;

    /**
     * Genomics Location（基因组学位置）
     */
    @ApiModelProperty(value = "Genomics Location（基因组学位置）")
    private String genomicsLocation;

    /**
     * Sample Name（样品名称）
     */
    @ApiModelProperty(value = "Sample Name（样品名称）")
    private String sampleName;

    /**
     * Description（描述）
     */
    @ApiModelProperty(value = "Description（描述）")
    private String description;

    /**
     * Tissue/Cell Line（组织/细胞系)
     */
    @ApiModelProperty(value = "Tissue/Cell Line（组织/细胞系)")
    private String tissueCellLine;

    /**
     * Gender（Male）
     */
    @ApiModelProperty(value = "Gender（Male）")
    private String gender;

    /**
     * Age（年龄）
     */
    @ApiModelProperty(value = "Age（年龄）")
    private String age;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    /**
     * Platform（分析仪）
     */
    @ApiModelProperty(value = "Platform（分析仪）")
    private String platform;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "颜色")
    private String color;

}

