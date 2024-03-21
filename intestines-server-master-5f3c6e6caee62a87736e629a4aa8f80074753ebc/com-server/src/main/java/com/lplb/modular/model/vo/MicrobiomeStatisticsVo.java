package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 15:40
 * @Description（描述）：MicrobiomeStatisticsVo
 */
@Data
@ApiModel(value = "Microbiome Statistics（Microbiome数据统计）")
public class MicrobiomeStatisticsVo {

    @ApiModelProperty(value = "Genera associated with Intestinal Diseases（1.2图数据共用）")
    private Map<String, Integer> generaAssociatedWithIntestinalDiseases;

    @ApiModelProperty(value = "Top 10 Genera associated with Intestinal Diseases")
    private Map<String, Integer> top10GeneraAssociatedWithIntestinalDiseases;

    @ApiModelProperty(value = "Species associated with Intestinal Diseases（3.4图数据共用）")
    private Map<String, Integer> speciesAssociatedWithIntestinalDiseases;

    @ApiModelProperty(value = "Top 10 Species associated with Intestinal Diseases")
    private Map<String, Integer> top10SpeciesAssociatedWithIntestinalDiseases;

}
