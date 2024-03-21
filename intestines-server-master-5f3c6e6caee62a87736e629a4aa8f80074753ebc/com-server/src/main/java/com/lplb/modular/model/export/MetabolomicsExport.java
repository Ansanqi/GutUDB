package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表实体类
 */
@Data
public class MetabolomicsExport {

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
     * Gut Microbiota（肠道菌群）
     */
    @ExcelProperty("Gut Microbiota")
    private String gutMicrobiota;

    /**
     * Metabolite（代谢物）
     */
    @ExcelProperty("Metabolite")
    private String metabolite;

    /**
     * species
     */
    @ExcelProperty("species")
    private String species;

    /**
     * Classification（分类）
     */
    @ExcelProperty("Classification")
    private String classification;

    /**
     * Description（描述）
     */
    @ExcelProperty("Description")
    private String description;

    /**
     * Sample Type（样本类型）
     */
    @ExcelProperty("Sample Type")
    private String sampleType;

    /**
     * Substrate（基质）
     */
    @ExcelProperty("Substrate")
    private String substrate;

    /**
     * Alteration（变更）
     */
    @ExcelProperty("Alteration")
    private String alteration;

    /**
     * Experimental Method（实验方法）
     */
    @ExcelProperty("Experimental Method")
    private String experimentalMethod;

    /**
     * Measurement Technique（测量技术）
     */
    @ExcelProperty("Measurement Technique")
    private String measurementTechnique;

    /**
     * Gene Association（基因关联）
     */
    @ExcelProperty("Gene Association")
    private String geneAssociation;

    @ExcelProperty("PMID Association")
    private String pmidAssociation;

}

