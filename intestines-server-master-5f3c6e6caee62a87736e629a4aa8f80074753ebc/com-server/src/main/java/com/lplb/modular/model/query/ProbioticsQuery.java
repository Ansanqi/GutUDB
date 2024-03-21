package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表实体类
 */
@Data
@ApiModel(value = "Probiotics（益生菌）(Probiotics)实体对象")
public class ProbioticsQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "probiotics")
    private String probiotics;

    @ApiModelProperty(value = "disease")
    private String disease;

    @ApiModelProperty(value = "genus")
    private String genus;

    @ApiModelProperty(value = "location")
    private String location;

}

