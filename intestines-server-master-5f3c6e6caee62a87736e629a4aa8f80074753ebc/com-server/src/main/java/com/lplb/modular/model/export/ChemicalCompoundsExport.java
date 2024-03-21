package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表实体类
 */
@Data
public class ChemicalCompoundsExport {

    /**
     * Disease related genes（疾病相关基因）
     */
    @ExcelProperty("Disease related genes")
    private String diseaseRelatedGenes;

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Species（种）
     */
    @ExcelProperty("Species")
    private String species;

    /**
     * Direct Evidence（直接证明）
     */
    @ExcelProperty("Direct Evidence")
    private String directEvidence;

    /**
     * Inference Network（推理网络）
     */
    @ExcelProperty("Inference Network")
    private String inferenceNetwork;

    /**
     * Source（来源）
     */
    @ExcelProperty("Source")
    private String source;

    /**
     * source跳转地址
     */
    @ExcelProperty("link")
    private String sourceUrl;

    /**
     * Reference Count（引用计数）
     */
    @ExcelProperty("Reference Count")
    private String referenceCount;

}

