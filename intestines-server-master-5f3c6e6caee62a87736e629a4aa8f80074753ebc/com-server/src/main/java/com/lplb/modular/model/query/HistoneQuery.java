package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)查询实体类
 */
@Data
@ApiModel(value = "Histone（组蛋白）(Histone)查询对象")
public class HistoneQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Histone（组蛋白）")
    private String histone;

    @ApiModelProperty(value = "Sapiens（人种）")
    private String sapiens;

    @ApiModelProperty(value = "Cell line/tissue（细胞系/组织）")
    private String cellLineTissue;

}

