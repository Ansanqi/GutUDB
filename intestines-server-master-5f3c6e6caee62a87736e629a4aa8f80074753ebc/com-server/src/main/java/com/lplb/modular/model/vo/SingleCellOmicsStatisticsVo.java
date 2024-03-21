package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 17:28
 * @Description（描述）：SingleCellOmicsStatisticsVo
 */
@Data
@ApiModel(value = "Single cell omics Statistics（Single cell omics数据统计）")
public class SingleCellOmicsStatisticsVo {

    @ApiModelProperty(value = "Cluster")
    private Map<String, Integer> cluster;

    @ApiModelProperty(value = "Top 10 Genes for Single-cell Alternative Splicing")
    private Map<String, Integer> top10GenesForSingleCellAlternativeSplicing;

}
