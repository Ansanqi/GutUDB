package com.lplb.core.enums;

import lombok.Getter;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-28 17:51
 * @Description（描述）：OmicsConstant
 */
@Getter
public enum OmicsCategoryEnums {

    HOME(1684872001978224642L, "Home"),
    OMICS(1684872279649538049L, "Omics"),
    NEWS(1686198472411811841L, "news"),
    ARTICLE(1686198673520300034L, "Article"),
    GENE_DETAIL(1686198924322902018L, "Gene"),
    PROJECT_DETAIL(1686199163192709122L, "Project"),
    EPIGEN_OMICS(1686199350988476417L, "Epigenome"),
    CHROMOSOME_STRUCTURE(1686209007660404738L, "Chromosome Structure"),
    DNA_METHYLATION(1686209051436355585L, "DNA Methylation"),
    HISTONE_MODIFICATION(1686209103374422017L, "Histone Modification"),
    GENOMICS(1686204120994254850L, "Genomics"),
    GENOMIC_ALTERATION(1686209520711864321L, "Genomic Alteration"),
    VIRULENCE_GENE(1686209633257623553L, "Virulence Gene"),
    CNA_GENES(1686209826929610753L, "CNA Genes"),
    MUTATED_GENES(1686209891060518913L, "Mutated Genes"),
    SNP(1686209969477226498L, "SNP"),
    STRUCTURAL_VARIANT_GENES(1686210328950050818L, "Structural Variant Genes"),
    ALTERNATIVE_SPLICING(1686313607784628226L, "Alternative Splicing"),
    GENE_EXPRESSION_DATA(1686313665489862657L, "Gene expression data"),
    NON_CODING_RNA(1686313721441878018L, "Non-coding RNA"),
    RNA_METHYLATION(1686313776034938881L, "RNA methylation"),
    SPATIAL_PMICS(1686206288262434817L, "Spatial omics"),
    SINGLE_CELL_PMICS(1686206507234463745L, "Single cell omics"),
    PROTE_OMICS(1686206586049630209L, "Proteomics"),
    MICROBI_OMICS(1684859599173771266L, "Microbiomics"),
    METABOLOMICS(1684859692723527682L, "Metabolomics");

    private final Long id;

    private final String name;

    OmicsCategoryEnums(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
