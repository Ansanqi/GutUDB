package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 9:44
 * @Description（描述）：StatisticsVo
 */
@Data
@ApiModel(value = "数据统计对象")
public class StatisticsVo {

    @ApiModelProperty(value = "名称")
    private String key;

    @ApiModelProperty(value = "数值")
    private Integer value;
}
