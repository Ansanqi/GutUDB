package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表实体类
 */
@Data
@ApiModel(value = "Chemical compounds（化合物）(ChemicalCompounds)实体对象")
public class ChemicalCompoundsQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "keywords")
    private String keywords;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Species（种）")
    private String species;

}

