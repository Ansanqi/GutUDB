package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 0:33
 * @Description（描述）：GenomicsStatisticsVo
 */
@Data
@ApiModel(value = "Genomics Statistics（Genomics数据统计）")
public class GenomicsStatisticsVo {

    @ApiModelProperty(value = "Genomic Alteration")
    private Map<String, Integer> genomicAlteration;

    @ApiModelProperty(value = "Top 10 CNA Genes")
    private Map<String, Integer> top10CnaGenes;

    @ApiModelProperty(value = "Top 10 Mutated Genes")
    private Map<String, Integer> top10MutatedGenes;

    @ApiModelProperty(value = "Top 10 SNP")
    private Map<String, Integer> top10Snp;

    @ApiModelProperty(value = "Top 10 Structural Variant Genes")
    private Map<String, Integer> top10StructuralVariantGenes;

    @ApiModelProperty(value = "Top 10 Virulence Genes")
    private Map<String, Integer> top10VirulenceGenes;
}
