package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：Spatial histology(SpatialHistology)表实体类
 */
@Data
public class SpatialHistologyExport {

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Species（物种）
     */
    @ExcelProperty("Species")
    private String species;

    /**
     * Tissue（组织）
     */
    @ExcelProperty("Tissue")
    private String tissue;

    /**
     * Biotech Categories（生物技术类别）
     */
    @ExcelProperty("Biotech Categories")
    private String biotechCategories;

    /**
     * Expression Range（表达式范围）
     */
    @ExcelProperty("Expression Range")
    private String expressionRange;

    /**
     * Biotech（生物技术）
     */
    @ExcelProperty("Biotech")
    private String biotech;

    /**
     * N Unit
     */
    @ExcelProperty("N Unit")
    private String nUnit;

    /**
     * Title（标题）
     */
    @ExcelProperty("Title")
    private String title;

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

}

