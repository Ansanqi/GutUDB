package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:31:44
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)导出实体类
 */
@Data
public class DnaMethylationExport implements Serializable {

    /**
     * Ensemble ID
     */
    @ExcelProperty("Ensemble ID")
    private String ensembleId;

    /**
     * Gene Name
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Chromosome
     */
    @ExcelProperty("Chromosome")
    private String chromosome;

    /**
     * Start Position
     */
    @ExcelProperty("Start Position")
    private Long startPosition;

    /**
     * End Position
     */
    @ExcelProperty("End Position")
    private Long endPosition;

    /**
     * CpG Length
     */
    @ExcelProperty("CpG Length")
    private Integer cpgLength;

    /**
     * CpG Number（编号）
     */
    @ExcelProperty("CpG Number")
    private Long cpgNumber;

    /**
     * Average Methylation Level（平均甲基化水平）
     */
    @ExcelProperty("Average Methylation Level")
    private BigDecimal averageMethylationLevel;

    /**
     * Strand（链）
     */
    @ExcelProperty("Strand")
    private String strand;

    /**
     * Project（项目）
     */
    @ExcelProperty("Project")
    private String project;

    /**
     * Sample ID（样本ID）
     */
    @ExcelProperty("Sample ID")
    private String sampleId;

    /**
     * Species（物种）
     */
    @ExcelProperty("Species")
    private String species;

    /**
     * Genomics Location（基因组学位置）
     */
    @ExcelProperty("Genomics Location")
    private String genomicsLocation;

    /**
     * Sample Name（样品名称）
     */
    @ExcelProperty("Sample Name")
    private String sampleName;

    /**
     * Description（描述）
     */
    @ExcelProperty("Description")
    private String description;

    /**
     * Tissue/Cell Line（组织/细胞系)
     */
    @ExcelProperty("Tissue/Cell Line")
    private String tissueCellLine;

    /**
     * Gender（Male）
     */
    @ExcelProperty("Gender")
    private String gender;

    /**
     * Age（年龄）
     */
    @ExcelProperty("Age")
    private String age;

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Platform（分析仪）
     */
    @ExcelProperty("Platform")
    private String platform;

}

