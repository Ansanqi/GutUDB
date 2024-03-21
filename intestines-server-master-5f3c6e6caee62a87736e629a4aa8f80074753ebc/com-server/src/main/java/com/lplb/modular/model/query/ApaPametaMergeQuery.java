package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：APA-pameta-merge(ApaPametaMerge)表实体类
 */
@Data
@ApiModel(value = "APA-pameta-merge(ApaPametaMerge)实体对象")
public class ApaPametaMergeQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    private String ensembleId;

    @ApiModelProperty(value = "Sample ID（样本ID）")
    private String sampleId;

    @ApiModelProperty(value = "Project（项目）")
    private String project;

    @ApiModelProperty(value = "Organism（生物）")
    private String organism;

    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    @ApiModelProperty(value = "Condition（环境）")
    private String conditions;

}

