package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.entity.BaseEntity;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 17:36:50
 * @Description（描述）：CNA Genes（CAN基因）(CnaGenes)查询实体类
 */
@Data
@ApiModel(value = "Genes（CAN基因）(CnaGenes)查询对象")
public class CnaGenesQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "CNA")
    private String cna;

    @ApiModelProperty(value = "keywords")
    private String keywords;
}

