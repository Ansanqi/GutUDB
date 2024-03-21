package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表实体类
 */
@Data
@ApiModel(value = "Gene_disease_omics(GeneDiseaseOmics)实体对象")
public class GeneDiseaseOmicsQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "疾病")
    private String diseaseName;

    @ApiModelProperty(value = "组学")
    private String omicsLevel;

    @ApiModelProperty(value = "基因")
    private String hotGenes;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "keywords")
    private String keywords;

}

