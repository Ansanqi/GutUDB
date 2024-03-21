package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表实体类
 */
@Data
@ApiModel(value = "GEO_information(GeoInformation)实体对象")
@TableName("in_geo_information")
public class GeoInformationQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "Project")
    private String project1;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Project（项目）")
    @TableField(value = "project")
    private String project;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Species（种）")
    private String species;

    @ApiModelProperty(value = "Tissue/Cell line")
    private String tissueCellLine;

}

