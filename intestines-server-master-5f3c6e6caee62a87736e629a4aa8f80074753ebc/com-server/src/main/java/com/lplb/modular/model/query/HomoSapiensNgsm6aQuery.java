package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表实体类
 */
@Data
@ApiModel(value = "HomoSapiens_NGSm6A(HomoSapiensNgsm6a)实体对象")
public class HomoSapiensNgsm6aQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Project（项目）")
    private String project;

    @ApiModelProperty(value = "Seqnames（序号）")
    private String seqnames;

    @ApiModelProperty(value = "Modification（改变）")
    private String modification;

    @ApiModelProperty(value = "Condition（环境）")
    private String conditions;

    @ApiModelProperty(value = "Technique（技术）")
    private String technique;
}

