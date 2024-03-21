package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-05 0:32
 * @Description（描述）：ColonDiseaseVo
 */
@Data
@ApiModel(value = "Colon Disease表头搜索疾病对象")
public class ColonDiseaseSearchVo implements Serializable {

    @ApiModelProperty(value = "疾病")
    private String name;

    @ApiModelProperty(value = "数量")
    private Integer count;

}
