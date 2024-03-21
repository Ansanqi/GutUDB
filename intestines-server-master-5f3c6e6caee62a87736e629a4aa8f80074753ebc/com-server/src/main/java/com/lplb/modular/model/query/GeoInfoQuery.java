package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：GEO_info（高通量基因表达信息）(GeoInfo)表实体类
 */
@Data
@ApiModel(value = "GEO_info（高通量基因表达信息）(GeoInfo)查询实体对象")
public class GeoInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "Data Access ID")
    private String dataAccessId1;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Data Access ID（数据ID）")
    private String dataAccessId;

    @ApiModelProperty(value = "Condition（环境）")
    private String conditions;

    @ApiModelProperty(value = "Cell line/Tissue/Organiod（细胞系/组织/有机物）")
    private String cellLineTissueOrganiod;

    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    @ApiModelProperty(value = "Type Category（类型类别）")
    private String typeCategory;

    @ApiModelProperty(value = "Cell Type（细胞类型）")
    private String cellType;

}

