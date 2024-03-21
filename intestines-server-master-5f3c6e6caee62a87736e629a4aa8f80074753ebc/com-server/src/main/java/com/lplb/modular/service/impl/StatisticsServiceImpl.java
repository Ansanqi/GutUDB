package com.lplb.modular.service.impl;

import com.lplb.modular.model.vo.*;
import com.lplb.modular.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-16 15:59
 * @Description（描述）：StatisticsServiceImpl
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private GeneDiseaseOmicsService geneDiseaseOmicsService;
    @Resource
    private ProteomicsService proteomicsService;
    @Resource
    private GeoInformationService geoInformationService;
    @Resource
    private GeneService geneService;
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
    private HistoneService histoneService;
    @Resource
    private DnaMethylationService dnaMethylationService;
    @Resource
    private MetabolomicsService metabolomicsService;
    @Resource
    private GeneraService generaService;
    @Resource
    private SpeciesService speciesService;
    @Resource
    private ChemicalCompoundsService chemicalCompoundsService;
    @Resource
    private ChineseMedicineService chineseMedicineService;
    @Resource
    private ProbioticsService probioticsService;
    @Resource
    private CircRnaService circRnaService;
    @Resource
    private LncRnaService lncRnaService;
    @Resource
    private MiRnaService miRnaService;
    @Resource
    private SingleCellGeneExpressDataService singleCellGeneExpressDataService;
    @Resource
    private AlternativeSplicingService alternativeSplicingService;
    @Resource
    private DiseaseStatisticsService diseaseStatisticsService;
    @Resource
    private PositionStatisticsService positionStatisticsService;

    /**
     * Data Statistics（数据统计）
     *
     * @return
     */
    @Override
    public DataStatisticsVo dataStatistics() {
        // 返回结果
        DataStatisticsVo result = new DataStatisticsVo();
        // 肠道疾病统计
//        Map<String, Integer> intestinalDiseases = geneDiseaseOmicsService.intestinalDiseases();
        // 原来的数据统计结果有误，使用新的数据表进行统计
        Map<String, Integer> intestinalDiseases = diseaseStatisticsService.intestinalDiseases();
        result.setIntestinalDiseases(intestinalDiseases);

        // 肠道部位统计
//        Map<String, Integer> position = proteomicsService.position();
        // 原来的数据统计结果有误，使用新的数据表进行统计
        Map<String, Integer> position = positionStatisticsService.position();
        result.setPosition(position);

        // 物种统计
        Map<String, Integer> species = geoInformationService.species();
        result.setSpecies(species);

        // Top 10 Genes（出现频次前十基因）
        // 原来的数据统计结果有误，使用新的数据表进行统计
        Map<String, Integer> top10Genes = diseaseStatisticsService.top10Genes();
        result.setTop10Genes(top10Genes);

        return result;
    }

    /**
     * Genomics数据统计
     *
     * @return
     */
    @Override
    public GenomicsStatisticsVo genomics() {
        // 返回结果
        GenomicsStatisticsVo result = new GenomicsStatisticsVo();

        // Genomic Alteration
        Map<String, Integer> genomicAlteration = this.genomicAlteration();
        result.setGenomicAlteration(genomicAlteration);

        // Top 10 CNA Genes
        Map<String, Integer> top10CnaGenes = cnaGenesService.top10CnaGenes();
        result.setTop10CnaGenes(top10CnaGenes);

        // Top 10 Mutated Genes
        Map<String, Integer> top10MutatedGenes = mutatedGenesService.top10MutatedGenes();
        result.setTop10MutatedGenes(top10MutatedGenes);

        // Top 10 SNP
        Map<String, Integer> top10Snp = snpService.top10Snp();
        result.setTop10Snp(top10Snp);

        // Top 10 Structural Variant Genes
        Map<String, Integer> top10StructuralVariantGenes = structuraVariantGenesService.top10StructuralVariantGenes();
        result.setTop10StructuralVariantGenes(top10StructuralVariantGenes);

        // Top 10 Virulence Genes
        Map<String, Integer> top10VirulenceGenes = virulenceGeneService.top10VirulenceGenes();
        result.setTop10VirulenceGenes(top10VirulenceGenes);
        return result;
    }

    /**
     * Genomic Alteration
     *
     * @return
     */
    private Map<String, Integer> genomicAlteration() {
        Map<String, Integer> result = new HashMap<>();

        int cnaCount = cnaGenesService.count();
        int mutatedCount = mutatedGenesService.count();
        int snpCount = snpService.count();
        int structuraCount = structuraVariantGenesService.count();

        result.put("CNA Genes", cnaCount);
        result.put("Mutated Genes", mutatedCount);
        result.put("SNP", snpCount);
        result.put("Structural Variant Genes", structuraCount);
        return result;
    }

    /**
     * 基因统计任务，后台用
     *
     * @return
     */
    @Override
    public Boolean geneTask() {
        // TODO 重新查询所有带有基因字段的表，统计基因数据出现频次
        return true;
    }

    /**
     * Epigenomics数据统计
     *
     * @return
     */
    @Override
    public EpigenomicsStatisticsVo epigenomics() {
        EpigenomicsStatisticsVo result = new EpigenomicsStatisticsVo();

        // Histone
        Map<String, Integer> histone = histoneService.histoneStatistics();
        result.setHistone(histone);

        // Top 10 Genes in DNA Methylation
        Map<String, Integer> top10GenesInDnaMethylation = dnaMethylationService.top10GenesInDnaMethylation();
        result.setTop10GenesInDnaMethylation(top10GenesInDnaMethylation);
        return result;
    }

    /**
     * Metabolomics数据统计
     *
     * @return
     */
    @Override
    public MetabolomicsStatisticsVo metabolomics() {
        MetabolomicsStatisticsVo result = new MetabolomicsStatisticsVo();

        Map<String, Integer> map = metabolomicsService.metaboliteStatistics();

//        // 数据总数
//        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
//        map.forEach((key, value) -> {
//            totalCount.updateAndGet(v -> v + value);
//        });
//        // 1%的总数量
//        Double percent = totalCount.get() * 0.01;

        // Metabolite
        Map<String, Integer> metabolite = new LinkedHashMap<>();
        map.forEach((key, value) -> {
            if (value == 1) {
                int other;
                if (metabolite.containsKey("other")) {
                    other = metabolite.get("other") + value;
                } else {
                    other = value;
                }
                metabolite.put("other", other);
            } else {
                metabolite.put(key, value);
            }
        });
        result.setMetabolite(metabolite);

        // Top 10 Metabolomics associated with Genes
        Map<String, Integer> top10MetabolomicsAssociatedWithGenes = new LinkedHashMap<>();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext() && i < 10) {
            Map.Entry<String, Integer> next = iterator.next();
            top10MetabolomicsAssociatedWithGenes.put(next.getKey(), next.getValue());
            i++;
        }
        result.setTop10MetabolomicsAssociatedWithGenes(top10MetabolomicsAssociatedWithGenes);
        return result;
    }

    /**
     * Protein数据统计
     *
     * @return
     */
    @Override
    public ProteomicsStatisticsVo proteomics() {
        ProteomicsStatisticsVo result = new ProteomicsStatisticsVo();

        Map<String, Integer> map = proteomicsService.proteinStatistics();

//        // 总数量
//        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
//        map.forEach((key, value) -> {
//            totalCount.updateAndGet(v -> v + value);
//        });
//        // 1%数量
//        Double percent = totalCount.get() * 0.01;
//
//        // Protein
//        Map<String, Integer> protein = new LinkedHashMap<>();
//        map.forEach((key, value) -> {
//            if (value <= percent) {
//                int other;
//                if (protein.containsKey("other")) {
//                    other = protein.get("other") + value;
//                } else {
//                    other = value;
//                }
//                protein.put("other", other);
//            } else {
//                protein.put(key, value);
//            }
//        });
        result.setProtein(map);

        // Top 10 Genes associated with Proteomics
        Map<String, Integer> top10GenesAssociatedWithProteomics = new LinkedHashMap<>();
        int i = 0;
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext() && i < 10) {
            Map.Entry<String, Integer> next = iterator.next();
            top10GenesAssociatedWithProteomics.put(next.getKey(), next.getValue());
            i++;
        }
        result.setTop10GenesAssociatedWithProteomics(top10GenesAssociatedWithProteomics);
        return result;
    }

    /**
     * Microbiome数据统计
     *
     * @return
     */
    @Override
    public MicrobiomeStatisticsVo microbiome() {
        MicrobiomeStatisticsVo result = new MicrobiomeStatisticsVo();

        // Genera
        Map<String, Integer> map = generaService.generaAssociatedWithIntestinalDiseases();

        Map<String, Integer> generaAssociatedWithIntestinalDiseases = new LinkedHashMap<>();

        // Top 10 Genera associated with Intestinal Diseases
        Map<String, Integer> top10GeneraAssociatedWithIntestinalDiseases = new LinkedHashMap<>();
        AtomicReference<Integer> top1 = new AtomicReference<>(0);
        map.forEach((key, value) -> {
            if (value == 1) {
                int other;
                if (generaAssociatedWithIntestinalDiseases.containsKey("other")) {
                    other = generaAssociatedWithIntestinalDiseases.get("other") + value;
                }else{
                    other = value;
                }
                generaAssociatedWithIntestinalDiseases.put("other", other);
            }else{
                generaAssociatedWithIntestinalDiseases.put(key, value);
            }
            if (top1.get() < 10) {
                top10GeneraAssociatedWithIntestinalDiseases.put(key, value);
                top1.getAndSet(top1.get() + 1);
            }

        });
        result.setGeneraAssociatedWithIntestinalDiseases(generaAssociatedWithIntestinalDiseases);
        result.setTop10GeneraAssociatedWithIntestinalDiseases(top10GeneraAssociatedWithIntestinalDiseases);

        // Species
        Map<String, Integer> map1 = speciesService.speciesAssociatedWithIntestinalDiseases();
        Map<String, Integer> top10SpeciesAssociatedWithIntestinalDiseases = new LinkedHashMap<>();
        AtomicReference<Integer> top2 = new AtomicReference<>(0);
        Map<String, Integer> speciesAssociatedWithIntestinalDiseases = new LinkedHashMap<>();
        map1.forEach((key, value) -> {
            if (value == 1) {
                int other;
                if (speciesAssociatedWithIntestinalDiseases.containsKey("other")) {
                    other = speciesAssociatedWithIntestinalDiseases.get("other") + value;
                }else {
                    other = value;
                }
                speciesAssociatedWithIntestinalDiseases.put("other", other);
            }else{
                speciesAssociatedWithIntestinalDiseases.put(key, value);
            }
            if (top2.get() < 10) {
                top10SpeciesAssociatedWithIntestinalDiseases.put(key, value);
                top2.getAndSet(top2.get() + 1);
            }
        });
        result.setSpeciesAssociatedWithIntestinalDiseases(speciesAssociatedWithIntestinalDiseases);
        result.setTop10SpeciesAssociatedWithIntestinalDiseases(top10SpeciesAssociatedWithIntestinalDiseases);
        return result;
    }

    /**
     * Therapy数据统计
     *
     * @return
     */
    @Override
    public TherapyStatisticsVo therapy() {
        TherapyStatisticsVo result = new TherapyStatisticsVo();

        // Chemical Compounds
        result = chemicalCompoundsService.chemicalCompoundsStatistics(result);
//        result.setChemicalCompounds(chemicalCompounds);

        // Chinese Medicine
        Map<String, Integer> map = chineseMedicineService.chineseMedicineStatistics();
        Map<String, Integer> chineseMedicine = new LinkedHashMap<>();
        Map<String, Integer> top10ChineseMedicineAssociatedWithIntestinalDiseases = new LinkedHashMap<>();
        AtomicReference<Integer> topi = new AtomicReference<>(0);
        map.forEach((key, value) -> {
            if (value == 1) {
                int other;
                if (chineseMedicine.containsKey("other")){
                    other = chineseMedicine.get("other") + value;
                }else {
                    other = value;
                }
                chineseMedicine.put("other", other);
            }else{
                chineseMedicine.put(key, value);
            }
            if (topi.get() < 10) {
                top10ChineseMedicineAssociatedWithIntestinalDiseases.put(key.trim(), value);
            }
            topi.getAndSet(topi.get() + 1);
        });

        result.setChineseMedicine(chineseMedicine);
        result.setTop10ChineseMedicineAssociatedWithIntestinalDiseases(top10ChineseMedicineAssociatedWithIntestinalDiseases);

        // Probiotics
        Map<String, Integer> probiotics = probioticsService.probioticsStatistics();
        result.setProbiotics(probiotics);

        // Top 10 Probiotics associated with Intestinal Diseases
        Map<String, Integer> top10ProbioticsAssociatedWithIntestinalDiseases = probioticsService.top10ProbioticsAssociatedWithIntestinalDiseases();
        result.setTop10ProbioticsAssociatedWithIntestinalDiseases(top10ProbioticsAssociatedWithIntestinalDiseases);
        return result;
    }

    /**
     * Transcriptomic数据统计
     *
     * @return
     */
    @Override
    public TranscriptomicStatisticsVo transcriptomic() {
        TranscriptomicStatisticsVo result = new TranscriptomicStatisticsVo();

        // Non-coding RNA
        Map<String, Integer> nonCodingRna = this.nonCodingRna();
        result.setNonCodingRna(nonCodingRna);

        // Top 10 circRNA
        Map<String, Integer> top10CircRna = circRnaService.top10CircRna();
        result.setTop10CircRna(top10CircRna);

        // Top 10 lnc RNA
        Map<String, Integer> top10LncRna = lncRnaService.top10LncRna();
        result.setTop10LncRna(top10LncRna);

        // Top 10 miRNA
        Map<String, Integer> top10MiRna = miRnaService.top10MiRna();
        result.setTop10MiRna(top10MiRna);

        return result;
    }

    /**
     * Single cell omics数据统计）
     *
     * @return
     */
    @Override
    public SingleCellOmicsStatisticsVo cluster() {
        SingleCellOmicsStatisticsVo result = new SingleCellOmicsStatisticsVo();

        // Cluster
        Map<String, Integer> cluster = singleCellGeneExpressDataService.clusterStatistics();
        result.setCluster(cluster);

        // Top 10 Genes for Single-cell Alternative Splicing
        Map<String, Integer> top10GenesForSingleCellAlternativeSplicing = alternativeSplicingService.top10GenesForSingleCellAlternativeSplicing();
        result.setTop10GenesForSingleCellAlternativeSplicing(top10GenesForSingleCellAlternativeSplicing);

        return result;
    }

    /**
     * Non-coding RNA
     *
     * @return
     */
    private Map<String, Integer> nonCodingRna() {
        Map<String, Integer> result = new HashMap<>();

        int circRnaCount = circRnaService.count();
        int lncRnaCount = lncRnaService.count();
        int miRnaCount = miRnaService.count();

        result.put("circRNA", circRnaCount);
        result.put("lncRNA", lncRnaCount);
        result.put("miRNA", miRnaCount);

        return result;
    }
}
