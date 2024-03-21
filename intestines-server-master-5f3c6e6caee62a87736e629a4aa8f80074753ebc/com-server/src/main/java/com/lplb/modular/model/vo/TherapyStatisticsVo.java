package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 16:06
 * @Description（描述）：TherapyStatisticsVo
 */
@Data
@ApiModel(value = "Therapy Statistics（Therapy数据统计）")
public class TherapyStatisticsVo {

    @ApiModelProperty(value = "Chemical Compounds（1.2图共用）")
    private Map<String, Integer> chemicalCompounds;

    @ApiModelProperty(value = "Top 10 Chemical Compounds associated with Intestinal Diseases")
    private Map<String, Integer> top10ChemicalCompoundsAssociatedWithIntestinalDiseases;

    @ApiModelProperty(value = "Chinese Medicine（3.4图共用）")
    private Map<String, Integer> chineseMedicine;

    @ApiModelProperty(value = "Top 10 Chinese Medicine associated with Intestinal Diseases")
    private Map<String, Integer> top10ChineseMedicineAssociatedWithIntestinalDiseases;

    @ApiModelProperty(value = "Probiotics")
    private Map<String, Integer> probiotics;

    @ApiModelProperty(value = "Top 10 Probiotics associated with Intestinal Diseases")
    private Map<String, Integer> top10ProbioticsAssociatedWithIntestinalDiseases;
}
