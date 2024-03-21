package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-05 0:37
 * @Description（描述）：ColonDiseaseHotGenesVo
 */
@Data
@ApiModel(value = "Colon Disease表头搜索Hot genes对象")
public class ColonDiseaseHotGenesVo implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
