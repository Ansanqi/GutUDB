package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-11 09:42:05
 * @Description（描述）：GSM6578068_mousecolon_protein(MousecolonProtein)表实体类
 */
@Data
@ApiModel(value = "GSM6578068_mousecolon_protein(MousecolonProtein)实体对象")
public class MousecolonProteinQuery extends BaseQuery {

    @ApiModelProperty(value = "搜索关键字")
    private String searchText;

    @ApiModelProperty(value = "keywords")
    private String keywords;
}

