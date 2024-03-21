package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-31 11:57
 * @Description（描述）：ChromosomeStructureQuery
 */
@Data
@ApiModel(value = "Chromosome structure（染色体结构）查询对象")
public class ChromosomeStructureQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "keywords")
    private String keywords;

    @ApiModelProperty(value = "Filename")
    private String fileName;
}
