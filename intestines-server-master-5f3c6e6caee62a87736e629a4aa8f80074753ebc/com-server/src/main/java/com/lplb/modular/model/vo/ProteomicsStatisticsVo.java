package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 15:09
 * @Description（描述）：ProteomicsStatisticsVo
 */
@Data
@ApiModel(value = "Proteomics Statistics（Proteomics数据统计）")
public class ProteomicsStatisticsVo {

    @ApiModelProperty(value = "Protein（1.2图数据共用）")
    private Map<String, Integer> protein;

    @ApiModelProperty(value = "Top 10 Genes associated with Proteomics")
    private Map<String, Integer> top10GenesAssociatedWithProteomics;
}
