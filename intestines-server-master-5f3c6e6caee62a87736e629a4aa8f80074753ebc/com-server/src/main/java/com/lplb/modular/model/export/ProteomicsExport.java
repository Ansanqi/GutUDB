package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Proteomics（蛋白质组学）(Proteomics)表实体类
 */
@Data
public class ProteomicsExport {

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Sapiens（智人）
     */
    @ExcelProperty("Sapiens")
    private String sapiens;

    /**
     * Position（位置）
     */
    @ExcelProperty("Position")
    private String position;

    /**
     * Protein Name（蛋白质名称）
     */
    @ExcelProperty("Protein Name")
    private String proteinName;

    /**
     * Induction（诱因）
     */
    @ExcelProperty("Induction")
    private String induction;

    /**
     * Length（长度）
     */
    @ExcelProperty("Length")
    private Integer length;

    /**
     * Post Translational Modification（翻译后修饰）
     */
    @ExcelProperty("Post Translational Modification")
    private String postTranslationalModification;

    /**
     * Function（功能）
     */
    @ExcelProperty("Function")
    private String function;

    /**
     * Activity Regulation（活动调节）
     */
    @ExcelProperty("Activity Regulation")
    private String activityRegulation;

    /**
     * Pathway（途径）
     */
    @ExcelProperty("Pathway")
    private String pathway;

    /**
     * PMID
     */
    @ExcelProperty("PMID")
    private String pmid;

    /**
     * PMID跳转URL
     */
    @ExcelProperty("pmidUrl")
    private String pmidUrl;

}

