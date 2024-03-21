package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：Spatial histology(SpatialHistology)表实体类
 */
@Data
@ApiModel(value = "Spatial histology(SpatialHistology)查询实体对象")
public class SpatialHistologyQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "Species（物种）")
    private String species;

    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    @ApiModelProperty(value = "Biotech Categories（生物技术类别）")
    private String biotechCategories;

    @ApiModelProperty(value = "Biotech（生物技术）")
    private String biotech;

    @ApiModelProperty(value = "keywords")
    private String keywords;
}

