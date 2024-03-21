package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表实体类
 */
@Data
@ApiModel(value = "Genera（属群）(Genera)实体对象")
public class GeneraQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Genera name（属群名称）")
    private String generaName;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Body site（身体部位）")
    private String bodySite;

    @ApiModelProperty(value = "查询关键字")
    private String keywords;
}

