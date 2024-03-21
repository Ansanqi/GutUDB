package com.lplb.core.enums;

import lombok.Getter;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-31 17:23
 * @Description（描述）：PmDataType
 */
@Getter
public enum PmDataType {

    CNA_GENES("CnaGenes", "CNA Genes（CAN基因）"),
    MUTATED_GENES("MutatedGenes", "Mutated Genes（突变基因）"),
    STRUCTURAL_VARIANT_GENES("StructuralVariantGenes", "Structural Variant Genes（结构变异基因）"),
    GEO_INFORMATION("GeoInformation", "GEO_information"),
    PROTE_OMICS("Proteomics", "Proteomics"),
    METABOLOMICS("Metabolomics", "Metabolomics"),
    PROBIOTICS("Probiotics", "Probiotics");

    private String name;

    private String desc;

    PmDataType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
