package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表实体类
 */
@Data
@ApiModel(value = "SraRunTable（Sra运行表）(SraRunTable)查询实体对象")
public class SraRunTableQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "Data Access ID")
    private String dataAccessId;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Cell Type（细胞类型）")
    private String celType;

    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    @ApiModelProperty(value = "Strain（系）")
    private String strain;

}

