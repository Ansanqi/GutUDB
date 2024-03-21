package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-05 0:34
 * @Description（描述）：ColonDiseaseOmicsLevelVo
 */
@Data
@ApiModel(value = "Colon Disease表头搜索Omics level对象")
public class ColonDiseaseOmicsLevelVo implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "子列表")
    private List<ColonDiseaseOmicsLevelVo> child;
}
