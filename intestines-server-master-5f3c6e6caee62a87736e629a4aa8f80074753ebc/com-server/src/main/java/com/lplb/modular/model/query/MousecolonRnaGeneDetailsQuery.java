package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 17:38
 * @Description（描述）：MousecolonRnaGeneDetailsQuery
 */
@Data
@ApiModel(value = "Proteomics详情实体对象")
public class MousecolonRnaGeneDetailsQuery extends BaseQuery {

    @ApiModelProperty(value = "ID")
    private Long id;
}
