package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SE.MATS.JCEC(SeMatsJcec)表实体类
 */
@Data
public class SeMatsJcecExport {

    /**
     * Data Access ID（数据ID）
     */
    @ExcelProperty("Data Access ID")
    private String dataAccessId;

    /**
     * 文件编号
     */
    @ExcelProperty("File")
    private Integer fileNo;

    /**
     * Ensemble ID（整体ID）
     */
    @ExcelProperty("Ensemble ID")
    private String ensembleId;

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * chr
     */
    @ExcelProperty("chr")
    private String chr;

    /**
     * Strand（链）
     */
    @ExcelProperty("Strand")
    private String strand;

    /**
     * exonStart_0base（显子起始0碱基）
     */
    @ExcelProperty("exonStart_0base")
    private Long exonStart0Base;

    /**
     * exonEnd（显子结束）
     */
    @ExcelProperty("exonEnd")
    private Long exonEnd;

    /**
     * upstreamES（上游ES）
     */
    @ExcelProperty("upstreamES")
    private Long upstreamEs;

    /**
     * upstreamEE（上游EE）
     */
    @ExcelProperty("upstreamEE")
    private Long upstreamEe;

    /**
     * downstreamES（下游ES）
     */
    @ExcelProperty("downstreamES")
    private Long downstreamEs;

    /**
     * downstreamEE（下游EE）
     */
    @ExcelProperty("downstreamEE")
    private Long downstreamEe;

    /**
     * ID
     */
    @ExcelProperty("ID")
    private Integer idNo;

    /**
     * IJC_SAMPLE_1（IJC样本1）
     */
    @ExcelProperty("IJC_SAMPLE_1")
    private String ijcSample1;

    /**
     * SJC_SAMPLE_1（SJC样本1）
     */
    @ExcelProperty("SJC_SAMPLE_1")
    private String sjcSample1;

    /**
     * IJC_SAMPLE_2（IJC样本2）
     */
    @ExcelProperty("IJC_SAMPLE_2")
    private String ijcSample2;

    /**
     * SJC_SAMPLE_2（SJC样本2）
     */
    @ExcelProperty("SJC_SAMPLE_2")
    private String sjcSample2;

    /**
     * IncFormLen
     */
    @ExcelProperty("IncFormLen")
    private Integer incFormLen;

    /**
     * SkipFormLen
     */
    @ExcelProperty("SkipFormLen")
    private Integer skipFormLen;

    /**
     * Pvalue（p值）
     */
    @ExcelProperty("Pvalue")
    private String pValue;

    /**
     * FDR
     */
    @ExcelProperty("FDR")
    private String fdr;

    /**
     * IncLevel1名称
     */
    @ExcelProperty("IncLevel1Name")
    private String incLevel1Name;

    /**
     * IncLevel1值
     */
    @ExcelProperty("IncLevel1")
    private String incLevel1;

    /**
     * IncLevel2名称
     */
    @ExcelProperty("IncLevel2Name")
    private String incLevel2Name;

    /**
     * IncLevel2值
     */
    @ExcelProperty("IncLevel2")
    private String incLevel2;

    /**
     * IncLevelDifference
     */
    @ExcelProperty("IncLevelDifference")
    private String incLevelDifference;

}

