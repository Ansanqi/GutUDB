package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-16 16:07
 * @Description（描述）：DataStatisticsVo
 */
@Data
@ApiModel(value = "Data Statistics（数据统计）")
public class DataStatisticsVo implements Serializable {

    @ApiModelProperty(value = "Intestinal Diseases（肠道疾病）")
    private Map<String, Integer> intestinalDiseases;

    @ApiModelProperty(value = "Position（肠道部位）")
    private Map<String, Integer> position;

    @ApiModelProperty(value = "Species（物种）")
    private Map<String, Integer> species;

    @ApiModelProperty(value = "Top 10 Genes（出现频次前十基因）")
    private Map<String, Integer> top10Genes;
}
