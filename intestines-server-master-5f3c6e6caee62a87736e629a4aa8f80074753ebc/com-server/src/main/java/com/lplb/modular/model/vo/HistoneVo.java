package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)表实体类
 */
@Data
@ApiModel(value = "Histone（组蛋白）(Histone)实体对象")
public class HistoneVo extends BaseEntity {

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
     * Histone（组蛋白）
     */
    @ApiModelProperty(value = "Histone（组蛋白）")
    private String histone;

    /**
     * Sapiens（人种）
     */
    @ApiModelProperty(value = "Sapiens（人种）")
    private String sapiens;

    /**
     * Cell line/tissue（细胞系/组织）
     */
    @ApiModelProperty(value = "Cell line/tissue（细胞系/组织）")
    private String cellLineTissue;

    /**
     * Chromosome（染色体)
     */
    @ApiModelProperty(value = "Chromosome（染色体)")
    private String chromosome;

    /**
     * Name of peak（峰值名称）
     */
    @ApiModelProperty(value = "Name of peak（峰值名称）")
    private String nameOfPeak;

    /**
     * Start position of peak（峰值开始位置）
     */
    @ApiModelProperty(value = "Start position of peak（峰值开始位置）")
    private Long startPositionOfPeak;

    /**
     * End position of peak（峰值结束位置）
     */
    @ApiModelProperty(value = "End position of peak（峰值结束位置）")
    private Long endPositionOfPeak;

    /**
     * Width of the peak（峰值宽度）
     */
    @ApiModelProperty(value = "Width of the peak（峰值宽度）")
    private Long widthOfThePeak;

    /**
     * Peak score（峰值）
     */
    @ApiModelProperty(value = "Peak score（峰值）")
    private Long peakScore;

    /**
     * Signal value of the peak（峰值标记）
     */
    @ApiModelProperty(value = "Signal value of the peak（峰值标记）")
    private BigDecimal signalValueOfThePeak;

    /**
     * Strand of the gene（基因链）
     */
    @ApiModelProperty(value = "Strand of the gene（基因链）")
    private String strandOfTheGene;

    /**
     * -log10(q-value)（q值）
     */
    @ApiModelProperty(value = " -log10(q-value)（q值）")
    private BigDecimal log10QValue;

    /**
     * -log10(p-value)
     */
    @ApiModelProperty(value = " -log10(p-value)")
    private BigDecimal log10PValue;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "颜色")
    private String color;

}

