package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：circRNA（环核糖核酸）(CircRna)表实体类
 */
@Data
@ApiModel(value = "circRNA（环核糖核酸）(CircRna)实体对象")
@TableName("in_circ_rna")
public class CircRnaQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Species（种）")
    private String species;

    @ApiModelProperty(value = "keywords")
    private String keywords;

}

