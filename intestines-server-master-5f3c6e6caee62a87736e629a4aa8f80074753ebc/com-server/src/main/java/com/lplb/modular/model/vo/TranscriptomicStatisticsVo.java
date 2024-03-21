package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 17:06
 * @Description（描述）：TranscriptomicStatisticsVo
 */
@Data
@ApiModel(value = "Transcriptomic Statistics（Transcriptomic数据统计）")
public class TranscriptomicStatisticsVo {

    @ApiModelProperty(value = "Non-coding RNA")
    private Map<String, Integer> nonCodingRna;

    @ApiModelProperty(value = "Top 10 circRNA")
    private Map<String, Integer> top10CircRna;

    @ApiModelProperty(value = "Top 10 lnc RNA")
    private Map<String, Integer> top10LncRna;

    @ApiModelProperty(value = "Top 10 miRNA")
    private Map<String, Integer> top10MiRna;
}
