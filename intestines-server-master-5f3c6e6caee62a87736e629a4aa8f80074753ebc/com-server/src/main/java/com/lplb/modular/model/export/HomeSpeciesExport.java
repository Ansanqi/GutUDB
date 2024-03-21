package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 23:04:30
 * @Description（描述）：Home | Species(HomeSpecies)表实体类
 */
@Data
public class HomeSpeciesExport {

    /**
     * Species
     */
    @ExcelProperty("Species")
    private String species;

    /**
     * Disease
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Disease related genes
     */
    @ExcelProperty("Disease related genes")
    private String diseaseRelatedGenes;

    /**
     * Direct Evidence
     */
    @ExcelProperty("Direct Evidence")
    private String directEvidence;

    /**
     * Inference Network
     */
    @ExcelProperty("Inference Network")
    private String inferenceNetwork;

    /**
     * Source
     */
    @ExcelProperty("Source")
    private String source;

    /**
     * Reference Count
     */
    @ExcelProperty("Reference Count")
    private String referenceCount;

    /**
     * link
     */
    @ExcelProperty("link")
    private String link;

}

