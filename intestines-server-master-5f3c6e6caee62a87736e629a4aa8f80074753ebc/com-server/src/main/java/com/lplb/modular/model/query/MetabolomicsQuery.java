package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表实体类
 */
@Data
@ApiModel(value = "Metabolomics（代谢组学）(Metabolomics)实体对象")
public class MetabolomicsQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "keywords")
    private String keywords;

    @ApiModelProperty(value = "disease")
    private String disease;

    @ApiModelProperty(value = "species")
    private String species;

    @ApiModelProperty(value = "alteration")
    private String alteration;


}

