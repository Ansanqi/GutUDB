package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 10:58
 * @Description（描述）：EpigenomicsStatisticsVo
 */
@Data
@ApiModel(value = "Epigenomics Statistics（Epigenomics数据统计）")
public class EpigenomicsStatisticsVo {

    @ApiModelProperty(value = "Histone")
    private Map<String, Integer> histone;

    @ApiModelProperty(value = "Top 10 Genes in DNA Methylation")
    private Map<String, Integer> top10GenesInDnaMethylation;
}
