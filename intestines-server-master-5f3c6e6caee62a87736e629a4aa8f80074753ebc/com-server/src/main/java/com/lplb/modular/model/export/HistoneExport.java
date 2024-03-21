package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)导出实体类
 */
@Data
public class HistoneExport implements Serializable {

    /**
     * Histone（组蛋白）
     */
    @ExcelProperty("Histone")
    private String histone;

    /**
     * Sapiens（人种）
     */
    @ExcelProperty("Sapiens")
    private String sapiens;

    /**
     * Cell line/tissue（细胞系/组织）
     */
    @ExcelProperty("Cell line/tissue")
    private String cellLineTissue;

    /**
     * Chromosome（染色体)
     */
    @ExcelProperty("Chromosome")
    private String chromosome;

    /**
     * Name of peak（峰值名称）
     */
    @ExcelProperty("Name of peak")
    private String nameOfPeak;

    /**
     * Start position of peak（峰值开始位置）
     */
    @ExcelProperty("Start position of peak")
    private Long startPositionOfPeak;

    /**
     * End position of peak（峰值结束位置）
     */
    @ExcelProperty("End position of peak")
    private Long endPositionOfPeak;

    /**
     * Width of the peak（峰值宽度）
     */
    @ExcelProperty("Width of the peak")
    private Long widthOfThePeak;

    /**
     * Peak score（峰值）
     */
    @ExcelProperty("Peak score")
    private Long peakScore;

    /**
     * Signal value of the peak（峰值标记）
     */

    @ExcelProperty("Signal value of the peak")
    private BigDecimal signalValueOfThePeak;

    /**
     * Strand of the gene（基因链）
     */
    @ExcelProperty("Strand of the gene")
    private String strandOfTheGene;

    /**
     * -log10(q-value)（q值）
     */
    @ExcelProperty(" -log10(q-value)")
    private BigDecimal log10QValue;

    /**
     * -log10(p-value)
     */
    @ExcelProperty(" -log10(p-value)")
    private BigDecimal log10PValue;

}

