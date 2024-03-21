package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 23:02:23
 * @Description（描述）：Home | Species(HomeSpecies)表实体类
 */
@Data
@ApiModel(value = "Home | Species(HomeSpecies)实体对象")
public class HomeSpeciesQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "keywords")
    private String keywords;

    @ApiModelProperty(value = "species")
    private String species;

    @ApiModelProperty(value = "species")
    private String disease;
}

