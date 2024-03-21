package com.lplb.modular.model.vo;

import com.lplb.modular.model.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-01 16:16:05
 * @Description（描述）：RBP and TF list表(RbpAndTfList)表实体类
 */
@Data
@ApiModel(value = "基因详情实体对象")
public class GeneDetailVo implements Serializable {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * Ensemble ID
     */
    @ApiModelProperty(value = "Ensemble ID")
    private String ensembleId;

    /**
     * Gene Name
     */
    @ApiModelProperty(value = "Gene Name")
    private String geneName;

    /**
     * Seqnames
     */
    @ApiModelProperty(value = "Seqnames")
    private String seqnames;

    /**
     * Start
     */
    @ApiModelProperty(value = "Start")
    private String start;

    /**
     * End
     */
    @ApiModelProperty(value = "End")
    private String end;

    /**
     * Strand
     */
    @ApiModelProperty(value = "Strand")
    private String strand;

    /**
     * RBP
     */
    @ApiModelProperty(value = "RBP")
    private String rbp;

    /**
     * m6A
     */
    @ApiModelProperty(value = "m6A")
    private String m6a;

    /**
     * AS
     */
    @ApiModelProperty(value = "AS")
    private String asFlag;

    /**
     * RNA edting
     */
    @ApiModelProperty(value = "RNA edting")
    private String rnaEdting;

    /**
     * TF
     */
    @ApiModelProperty(value = "TF")
    private String tf;

    /**
     * Motif
     */
    @ApiModelProperty(value = "Motif")
    private String motif;

    /**
     * Therapeutic targets
     */
    @ApiModelProperty(value = "Therapeutic targets")
    private String therapeuticTargets;

    /**
     * Drug
     */
    @ApiModelProperty(value = "Drug")
    private String drug;

    /**
     * Profile
     */
    @ApiModelProperty(value = "Profile")
    private String profile;

    /**
     * NCBI Association
     */
    @ApiModelProperty(value = "NCBI Association")
    private String ncbiAssociation;

    /**
     * Genomics
     */
    @ApiModelProperty(value = "Genomics")
    private String genomics;

    /**
     * Metabolomics
     */
    @ApiModelProperty(value = "Metabolomics")
    private String metabolomics;

    /**
     * Proteomics
     */
    @ApiModelProperty(value = "Proteomics")
    private String proteomics;

    /**
     * Single cell and spatial omics data
     */
    @ApiModelProperty(value = "Single cell and spatial omics data")
    private String singleCellAndSpatialOmicsData;

    /**
     * Transcriptomic
     */
    @ApiModelProperty(value = "Transcriptomic")
    private String transcriptomic;

    @ApiModelProperty(value = "Chemical compounds")
    private List<ChemicalCompounds> chemicalCompounds;

    @ApiModelProperty(value = "Chinese Medicine")
    private List<ChineseMedicine> chineseMedicines;

    @ApiModelProperty(value = "TPM Exp")
    private List<ARnaCleanedLogTpmVo> tpmExps;

    @ApiModelProperty(value = "TS Score")
    private List<DRnaTsScoreVo> tsScore;

    @ApiModelProperty(value = "Boxplot&Dotplot")
    private List<GeneExpressionDataVo> boxlotDotplots;

    @ApiModelProperty(value = "Survival analysis")
    private String survivalAnalysis;

    @ApiModelProperty(value = "Chromosome Structure")
    private List<ChromosomeStructure> chromosomeStructures;

    @ApiModelProperty(value = "DNA Methylation")
    private List<DnaMethylation> dnaMethylations;

    @ApiModelProperty(value = "Histone Modification")
    private List<Histone> histones;

    @ApiModelProperty(value = "CNA Genes")
    private List<CnaGenes> cnaGenes;

    @ApiModelProperty(value = "Mutated Genes")
    private List<MutatedGenes> mutatedGenes;

    @ApiModelProperty(value = "SNP")
    private List<Snp> snps;

    @ApiModelProperty(value = "Structural Variant Genes")
    private List<StructuraVariantGenes> structuraVariantGenes;

    @ApiModelProperty(value = "Virulence gene")
    private List<VirulenceGene> virulenceGenes;

    @ApiModelProperty(value = "Alternative Splicing")
    private List<GeoInfo> geoInfos;

    @ApiModelProperty(value = "circRNA")
    private List<CircRna> circRnas;

    @ApiModelProperty(value = "lnc RNA")
    private List<LncRna> lncRnas;

    @ApiModelProperty(value = "miRNA")
    private List<MiRna> miRnas;

    @ApiModelProperty(value = "HomoSapiens_NGSm6A")
    private List<HomoSapiensNgsm6a> homoSapiensNgsm6as;

    @ApiModelProperty(value = "Spatial Omics")
    private List<SpatialHistology> spatialHistologies;

    @ApiModelProperty(value = "Gene expression data")
    private List<SingleCellGeneExpressData> singleCellGeneExpressData;

    @ApiModelProperty(value = "alternative polyadenylation")
    private List<ApaPametaMerge> apaPametaMerges;

    @ApiModelProperty(value = "Alternative splicing")
    private List<AlternativeSplicing> alternativeSplicings;

    @ApiModelProperty(value = "Proteomics")
    private List<Proteomics> proteomicsList;

    @ApiModelProperty(value = "Associated Genera")
    private List<Genera> generas;

    @ApiModelProperty(value = "Associated Species")
    private List<Species> species;

    @ApiModelProperty(value = "Metabol Omics")
    private List<Metabolomics> metabolomicsList;

}

