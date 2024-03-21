package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 17:13
 * @Description（描述）：MousecolonRnaGenevo
 */
@Data
@ApiModel(value = "Proteomics实体对象")
public class MousecolonRnaGeneVo implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "基因名称")
    private String geneName;

    @ApiModelProperty(value = "数据列表")
    private List<Map<String, Object>> details;
}
