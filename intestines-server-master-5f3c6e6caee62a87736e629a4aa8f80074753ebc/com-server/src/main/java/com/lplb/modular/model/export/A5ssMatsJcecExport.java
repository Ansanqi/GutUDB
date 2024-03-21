package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A5SS.MATS.JCEC(A5ssMatsJcec)表实体类
 */
@Data
public class A5ssMatsJcecExport {

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
     * longExonStart_0base（长外显子起始0碱基）
     */
    @ExcelProperty("longExonStart_0base")
    private Long longExonStart0Base;

    /**
     * longExonEnd（长外显子结束）
     */
    @ExcelProperty("longExonEnd")
    private Long longExonEnd;

    /**
     * shortES（短ES）
     */
    @ExcelProperty("shortES")
    private Long shortEs;

    /**
     * shortEE（短EE）
     */
    @ExcelProperty("shortEE")
    private Long shortEe;

    /**
     * flankingES（侧面ES）
     */
    @ExcelProperty("flankingES")
    private Long flankingEs;

    /**
     * flankingEE（侧面EE）
     */
    @ExcelProperty("flankingEE")
    private Long flankingEe;

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

