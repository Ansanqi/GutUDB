package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表实体类
 */
@Data
@ApiModel(value = "Alternative splicing（选择性剪接）(AlternativeSplicing)实体对象")
public class AlternativeSplicingQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "Cluster1（群集1）")
    private String cluster1;

    @ApiModelProperty(value = "Cluster2（群集2）")
    private String cluster2;

    @ApiModelProperty(value = "keywords")
    private String keywords;

}

