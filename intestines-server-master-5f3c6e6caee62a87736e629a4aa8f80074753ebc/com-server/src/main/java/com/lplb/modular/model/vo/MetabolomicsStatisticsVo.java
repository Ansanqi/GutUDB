package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 11:21
 * @Description（描述）：MetabolomicsStatisticsVo
 */
@Data
@ApiModel(value = "Metabolomics Statistics（Metabolomics数据统计）、1.2图数据共用")
public class MetabolomicsStatisticsVo {

    @ApiModelProperty(value = "Metabolite")
    private Map<String, Integer> metabolite;

    @ApiModelProperty(value = "Metabolite")
    private Map<String, Integer> top10MetabolomicsAssociatedWithGenes;
}
