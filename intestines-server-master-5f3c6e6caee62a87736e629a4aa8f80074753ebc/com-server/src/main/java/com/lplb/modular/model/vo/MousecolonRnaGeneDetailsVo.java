package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 17:13
 * @Description（描述）：MousecolonRnaGenevo
 */
@Data
@ApiModel(value = "Proteomics详情实体对象")
public class MousecolonRnaGeneDetailsVo implements Serializable {

    @ApiModelProperty(value = "key")
    private String detailsKey;

    @ApiModelProperty(value = "value")
    private String detailsValue;
}
