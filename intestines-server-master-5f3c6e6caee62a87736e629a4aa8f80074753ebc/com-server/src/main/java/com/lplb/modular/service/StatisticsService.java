package com.lplb.modular.service;

import com.lplb.modular.model.vo.*;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-16 15:59
 * @Description（描述）：StatisticsService
 */
public interface StatisticsService {

    /**
     * Data Statistics（数据统计）
     *
     * @return
     */
    DataStatisticsVo dataStatistics();

    /**
     * Genomics数据统计
     *
     * @return
     */
    GenomicsStatisticsVo genomics();

    /**
     * 基因统计任务，后台用
     *
     * @return
     */
    Boolean geneTask();

    /**
     * Epigenomics数据统计
     *
     * @return
     */
    EpigenomicsStatisticsVo epigenomics();

    /**
     * Metabolomics数据统计
     *
     * @return
     */
    MetabolomicsStatisticsVo metabolomics();

    /**
     * Protein数据统计
     *
     * @return
     */
    ProteomicsStatisticsVo proteomics();

    /**
     * Microbiome数据统计
     *
     * @return
     */
    MicrobiomeStatisticsVo microbiome();

    /**
     * Therapy数据统计
     *
     * @return
     */
    TherapyStatisticsVo therapy();

    /**
     * Transcriptomic数据统计
     *
     * @return
     */
    TranscriptomicStatisticsVo transcriptomic();

    /**
     * Single cell omics数据统计）
     *
     * @return
     */
    SingleCellOmicsStatisticsVo cluster();
}
