package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.model.entity.*;
import com.lplb.modular.model.vo.ARnaCleanedLogTpmVo;
import com.lplb.modular.model.vo.DRnaTsScoreVo;
import com.lplb.modular.model.vo.GeneDetailVo;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import com.lplb.modular.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-01 16:43
 * @Description（描述）：GeneDetailServiceImpl
 */
@Service
@Slf4j
@Transactional
public class GeneDetailServiceImpl implements GeneDetailService {

    @Value("${file.requestPath}")
    private String fileRequestPath;

    @Resource
    private RbpAndTfListService rbpAndTfListService;
    @Resource
    private ChemicalCompoundsService chemicalCompoundsService;
    @Resource
    private ChineseMedicineService chineseMedicineService;
    @Resource
    private ARnaCleanedLogTpmService aRnaCleanedLogTpmService;
    @Resource
    private DRnaTsScoreService dRnaTsScoreService;
    @Resource
    private GeneExpressionDataService geneExpressionDataService;
    @Resource
    private ChromosomeStructureService chromosomeStructureService;
    @Resource
    private DnaMethylationService dnaMethylationService;
    @Resource
    private HistoneService histoneService;
    @Resource
    private CnaGenesService cnaGenesService;
    @Resource
    private MutatedGenesService mutatedGenesService;
    @Resource
    private SnpService snpService;
    @Resource
    private StructuraVariantGenesService structuraVariantGenesService;
    @Resource
    private VirulenceGeneService virulenceGeneService;
    @Resource
    private GeoInfoService geoInfoService;
    @Resource
    private CircRnaService circRnaService;
    @Resource
    private LncRnaService lncRnaService;
    @Resource
    private MiRnaService miRnaService;
    @Resource
    private HomoSapiensNgsm6aService homoSapiensNgsm6aService;
    @Resource
    private SpatialHistologyService spatialHistologyService;
    @Resource
    private SingleCellGeneExpressDataService singleCellGeneExpressDataService;
    @Resource
    private ApaPametaMergeService apaPametaMergeService;
    @Resource
    private AlternativeSplicingService alternativeSplicingService;
    @Resource
    private ProteomicsService proteomicsService;
    @Resource
    private GeneraService generaService;
    @Resource
    private SpeciesService speciesService;
    @Resource
    private MetabolomicsService metabolomicsService;
    @Resource
    private ColonDiseaseService colonDiseaseService;
    @Resource
    private TcgaCoadExpService tcgaCoadExpService;

    /**
     * 查询筛选疾病列表
     *
     * @return
     */
    @Override
    public List<String> diseaseList() {
        // 先查询肠道疾病name列表
        LambdaQueryWrapper<ColonDisease> colonDiseaseWrapper = new LambdaQueryWrapper<>();
        colonDiseaseWrapper.select(ColonDisease::getName);
        colonDiseaseWrapper.orderByAsc(ColonDisease::getName);
        colonDiseaseWrapper.groupBy(ColonDisease::getName);
        List<ColonDisease> colonDiseases = colonDiseaseService.list(colonDiseaseWrapper);
        List<String> diseaseNames = colonDiseases.stream().map(item -> item.getName()).collect(Collectors.toList());
        return diseaseNames;
    }

    /**
     * 根据基因名称查询基因详情
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public GeneDetailVo geneDetailByGeneName(String geneName, String disease) {
        // 查询RBP and TF list表数据
        RbpAndTfList rbpAndTfList = rbpAndTfListService.getByGeneName(geneName);
        if (ObjectUtils.isEmpty(rbpAndTfList)) {
            return null;
        }

        // 返回结果
        GeneDetailVo result = BeanConvertUtil.convert(rbpAndTfList, GeneDetailVo.class);

        // 查询西药列表
        List<ChemicalCompounds> chemicalCompounds = chemicalCompoundsService.listByGene(geneName, disease);
        result.setChemicalCompounds(chemicalCompounds);

        // 查询中药列表
        List<ChineseMedicine> chineseMedicines = chineseMedicineService.listByGene(geneName, disease);
        result.setChineseMedicines(chineseMedicines);

        // 查询TPM Exp
        List<ARnaCleanedLogTpmVo> tpmExps = aRnaCleanedLogTpmService.listByGeneName(geneName);
        result.setTpmExps(tpmExps);

        // TS Score
        List<DRnaTsScoreVo> tsScore = dRnaTsScoreService.listByGeneName(geneName);
        result.setTsScore(tsScore);

        // Boxplot&Dotplot
        List<GeneExpressionDataVo> boxlotDotplots = tcgaCoadExpService.listByGeneName(geneName);
        result.setBoxlotDotplots(boxlotDotplots);

        // Survival analysis
        String survivalAnalysis = fileRequestPath + "imgs/IntestineDB/03.Transcriptomic/Gene_expression_data/Homo_sapiens/Survival_Analysis/COAD/"
                + geneName + ".png";
        result.setSurvivalAnalysis(survivalAnalysis);


        // Chromosome Structure
        List<ChromosomeStructure> chromosomeStructures = chromosomeStructureService.listByGeneName(geneName);
        result.setChromosomeStructures(chromosomeStructures);

        // DNA Methylation
        List<DnaMethylation> dnaMethylations = dnaMethylationService.listByGeneName(geneName, disease);
        result.setDnaMethylations(dnaMethylations);

        // Histone Modification
        List<Histone> histones = histoneService.listByGeneName(geneName);
        result.setHistones(histones);

        // CNA Genes
        List<CnaGenes> cnaGenes = cnaGenesService.listByGeneName(geneName, disease);
        result.setCnaGenes(cnaGenes);

        // Mutated Genes
        List<MutatedGenes> mutatedGenes = mutatedGenesService.listByGeneName(geneName, disease);
        result.setMutatedGenes(mutatedGenes);

        // SNP
        List<Snp> snps = snpService.listByGeneName(geneName, disease);
        result.setSnps(snps);

        // Structural Variant Genes
        List<StructuraVariantGenes> structuraVariantGenes = structuraVariantGenesService.listByGeneName(geneName, disease);
        result.setStructuraVariantGenes(structuraVariantGenes);

        // Virulence gene
        List<VirulenceGene> virulenceGenes = virulenceGeneService.listByGeneName(geneName, disease);
        result.setVirulenceGenes(virulenceGenes);

        // Alternative Splicing
        List<GeoInfo> geoInfos = geoInfoService.listByGeneName(geneName);
        result.setGeoInfos(geoInfos);

        // circRNA
        List<CircRna> circRnas = circRnaService.listByGeneName(geneName, disease);
        result.setCircRnas(circRnas);

        // lnc RNA
        List<LncRna> lncRnas = lncRnaService.listByGeneName(geneName, disease);
        result.setLncRnas(lncRnas);

        // miRNA
        List<MiRna> miRnas = miRnaService.listByGeneName(geneName, disease);
        result.setMiRnas(miRnas);

        // HomoSapiens_NGSm6A
        List<HomoSapiensNgsm6a> homoSapiensNgsm6as = homoSapiensNgsm6aService.listByGeneName(geneName);
        result.setHomoSapiensNgsm6as(homoSapiensNgsm6as);

        // Spatial Omics
        List<SpatialHistology> spatialHistologies = spatialHistologyService.listByGeneName(geneName);
        result.setSpatialHistologies(spatialHistologies);

        // Gene expression data
        List<SingleCellGeneExpressData> singleCellGeneExpressData = singleCellGeneExpressDataService.listByGeneName(geneName);
        result.setSingleCellGeneExpressData(singleCellGeneExpressData);

        // alternative polyadenylation
        List<ApaPametaMerge> apaPametaMerges = apaPametaMergeService.listByGeneName(geneName);
        result.setApaPametaMerges(apaPametaMerges);

        // Alternative splicing
        List<AlternativeSplicing> alternativeSplicings = alternativeSplicingService.listByGeneName(geneName);
        result.setAlternativeSplicings(alternativeSplicings);

        // Proteomics
        List<Proteomics> proteomicsList = proteomicsService.listByGeneName(geneName, disease);
        result.setProteomicsList(proteomicsList);

        // Associated Genera
        List<Genera> generas = generaService.listByGeneName(geneName, disease);
        result.setGeneras(generas);

        // Associated Species
        List<Species> species = speciesService.listByGeneName(geneName, disease);
        result.setSpecies(species);

        // Metabol Omics
        List<Metabolomics> metabolomicsList = metabolomicsService.listByGeneName(geneName, disease);
        result.setMetabolomicsList(metabolomicsList);

        return result;
    }
}
