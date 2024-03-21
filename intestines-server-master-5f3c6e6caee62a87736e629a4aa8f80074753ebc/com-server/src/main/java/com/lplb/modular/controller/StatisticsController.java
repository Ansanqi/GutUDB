package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.vo.*;
import com.lplb.modular.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-16 15:57
 * @Description（描述）：StatisticsController
 */
@RestController
@RequestMapping("/web/statistics")
@Api(value = "StatisticsController", tags = "Statistics（统计）")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @GetMapping(value = "/dataStatistics")
    @ApiOperation(value = "Data Statistics（数据统计）")
    public ResponseData<DataStatisticsVo> dataStatistics() {
        DataStatisticsVo result = statisticsService.dataStatistics();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/genomics")
    @ApiOperation(value = "Genomics Statistics（Genomics数据统计）")
    public ResponseData<GenomicsStatisticsVo> genomics() {
        GenomicsStatisticsVo result = statisticsService.genomics();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/epigenomics")
    @ApiOperation(value = "Epigenomics Statistics（Epigenomics数据统计）")
    public ResponseData<EpigenomicsStatisticsVo> epigenomics() {
        EpigenomicsStatisticsVo result = statisticsService.epigenomics();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/geneTask")
    @ApiOperation(value = "基因统计任务，后台用")
    public ResponseData<Boolean> geneTask() {
        Boolean result = statisticsService.geneTask();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/metabolomics")
    @ApiOperation(value = "Metabolomics Statistics（Metabolomics数据统计）")
    public ResponseData<MetabolomicsStatisticsVo> metabolomics() {
        MetabolomicsStatisticsVo result = statisticsService.metabolomics();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/proteomics")
    @ApiOperation(value = "Proteomics Statistics（Proteomics数据统计）")
    public ResponseData<ProteomicsStatisticsVo> proteomics() {
        ProteomicsStatisticsVo result = statisticsService.proteomics();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/microbiome")
    @ApiOperation(value = "Microbiome Statistics（Microbiome数据统计）")
    public ResponseData<MicrobiomeStatisticsVo> microbiome() {
        MicrobiomeStatisticsVo result = statisticsService.microbiome();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/therapy")
    @ApiOperation(value = "Therapy Statistics（Therapy数据统计）")
    public ResponseData<TherapyStatisticsVo> therapy() {
        TherapyStatisticsVo result = statisticsService.therapy();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/transcriptomic")
    @ApiOperation(value = "Transcriptomic Statistics（Transcriptomic数据统计）")
    public ResponseData<TranscriptomicStatisticsVo> transcriptomic() {
        TranscriptomicStatisticsVo result = statisticsService.transcriptomic();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/singleCellOmics")
    @ApiOperation(value = "Single cell omics Statistics（Single cell omics数据统计）")
    public ResponseData<SingleCellOmicsStatisticsVo> singleCellOmics() {
        SingleCellOmicsStatisticsVo result = statisticsService.cluster();
        return ResponseData.success(result);
    }
}
