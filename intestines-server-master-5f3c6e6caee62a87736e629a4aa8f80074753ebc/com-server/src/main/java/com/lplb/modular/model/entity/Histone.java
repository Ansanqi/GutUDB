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
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)表实体类
 */
@Data
@ApiModel(value = "Histone（组蛋白）(Histone)实体对象")
@TableName("in_histone")
public class Histone extends BaseEntity {

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
     * Histone（组蛋白）
     */
    @ApiModelProperty(value = "Histone（组蛋白）")
    @TableField(value = "histone")
    private String histone;

    /**
     * Sapiens（人种）
     */
    @ApiModelProperty(value = "Sapiens（人种）")
    @TableField(value = "sapiens")
    private String sapiens;

    /**
     * Cell line/tissue（细胞系/组织）
     */
    @ApiModelProperty(value = "Cell line/tissue（细胞系/组织）")
    @TableField(value = "cell_line_tissue")
    private String cellLineTissue;

    /**
     * Chromosome（染色体)
     */
    @ApiModelProperty(value = "Chromosome（染色体)")
    @TableField(value = "chromosome")
    private String chromosome;

    /**
     * Name of peak（峰值名称）
     */
    @ApiModelProperty(value = "Name of peak（峰值名称）")
    @TableField(value = "name_of_peak")
    private String nameOfPeak;

    /**
     * Start position of peak（峰值开始位置）
     */
    @ApiModelProperty(value = "Start position of peak（峰值开始位置）")
    @TableField(value = "start_position_of_peak")
    private Long startPositionOfPeak;

    /**
     * End position of peak（峰值结束位置）
     */
    @ApiModelProperty(value = "End position of peak（峰值结束位置）")
    @TableField(value = "end_position_of_peak")
    private Long endPositionOfPeak;

    /**
     * Width of the peak（峰值宽度）
     */
    @ApiModelProperty(value = "Width of the peak（峰值宽度）")
    @TableField(value = "width_of_the_peak")
    private Long widthOfThePeak;

    /**
     * Peak score（峰值）
     */
    @ApiModelProperty(value = "Peak score（峰值）")
    @TableField(value = "peak_score")
    private Long peakScore;

    /**
     * Signal value of the peak（峰值标记）
     */
    @ApiModelProperty(value = "Signal value of the peak（峰值标记）")
    @TableField(value = "signal_value_of_the_peak")
    private BigDecimal signalValueOfThePeak;

    /**
     * Strand of the gene（基因链）
     */
    @ApiModelProperty(value = "Strand of the gene（基因链）")
    @TableField(value = "strand_of_the_gene")
    private String strandOfTheGene;

    /**
     *  -log10(q-value)（q值）
     */
    @ApiModelProperty(value = " -log10(q-value)（q值）")
    @TableField(value = "log_10_q_value")
    private BigDecimal log10QValue;

    /**
     *  -log10(p-value)
     */
    @ApiModelProperty(value = " -log10(p-value)")
    @TableField(value = "log_10_p_value")
    private BigDecimal log10PValue;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

