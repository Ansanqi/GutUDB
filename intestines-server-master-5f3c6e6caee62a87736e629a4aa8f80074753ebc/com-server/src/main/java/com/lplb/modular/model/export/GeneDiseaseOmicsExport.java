package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表实体类
 */
@Data
public class GeneDiseaseOmicsExport {

    /**
     * Disease related genes
     */
    @ExcelProperty("Disease related genes")
    private String diseaseRelatedGenes;

    /**
     * Disease
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Omics
     */
    @ExcelProperty("Omics")
    private String omics;

    /**
     * Source_url
     */
    @ExcelProperty("Source")
    private String sources;

    @ExcelProperty("url")
    private String url;

    @ExcelProperty("Type")
    private String typess;

    @ExcelProperty("omicsLevel")
    private String omicsLevel;

}

