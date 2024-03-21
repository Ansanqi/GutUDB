package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表实体类
 */
@Data
@ApiModel(value = "Gene expression data（基因表达数据）(GeneExpressionData)实体对象")
@TableName("in_gene_expression_data")
public class GeneExpressionDataQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "类型（Homo sapiens/Mus musculus/Rattus norvegicus/Sus scrofa）")
    @TableField(value = "exp_type")
    private String expType;

    @ApiModelProperty(value = "Project")
    private String project;

    @ApiModelProperty(value = "Sample")
    private String sample;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

}

