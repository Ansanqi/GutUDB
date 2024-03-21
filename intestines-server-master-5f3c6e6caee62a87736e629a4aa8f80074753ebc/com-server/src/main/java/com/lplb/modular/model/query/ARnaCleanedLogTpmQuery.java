package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表实体类
 */
@Data
@ApiModel(value = "A RNA cleaned log TPM(ARnaCleanedLogTpm)实体对象")
public class ARnaCleanedLogTpmQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "Project", required = true)
    private String project;

    @ApiModelProperty(value = "Sample", required = true)
    private String sample;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

}

