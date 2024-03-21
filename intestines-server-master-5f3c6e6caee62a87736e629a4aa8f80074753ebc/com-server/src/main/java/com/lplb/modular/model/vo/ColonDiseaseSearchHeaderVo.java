package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-05 0:30
 * @Description（描述）：ColonDiseaseSearchHeaderVo
 */
@Data
@ApiModel(value = "Colon Disease表头搜索对象")
public class ColonDiseaseSearchHeaderVo implements Serializable {

    @ApiModelProperty(value = "Diseases name")
    private List<ColonDiseaseSearchVo> diseases;

    @ApiModelProperty(value = "Omics level")
    private List<ColonDiseaseOmicsLevelVo> omicsLevels;

    @ApiModelProperty(value = "Hot genes")
    private List<ColonDiseaseHotGenesVo> hotGeness;

}
