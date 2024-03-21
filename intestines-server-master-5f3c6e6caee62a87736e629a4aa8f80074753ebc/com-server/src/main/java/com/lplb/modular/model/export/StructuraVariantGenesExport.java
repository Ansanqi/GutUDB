package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Structural Variant Genes（结构变异基因）(StructuraVariantGenes)导出实体类
 */
@Data
public class StructuraVariantGenesExport implements Serializable {

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
     * Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））
     */
    @ExcelProperty("Is Cancer Gene (source: OncoKB)")
    private String isCancerGeneSourceOncoKb;

    /**
     * Freq（频率）
     */
    @ExcelProperty("Freq")
    private String freq;

    /**
     * PMID
     */
    @ExcelProperty("PMID")
    private String pmid;

}

