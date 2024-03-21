package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：MXE.MATS.JCEC(MxeMatsJcec)表实体类
 */
@Data
@ApiModel(value = "MXE.MATS.JCEC(MxeMatsJcec)实体对象")
public class MxeMatsJcecQuery extends BaseQuery {

    @ApiModelProperty(value = "Data Access ID")
    private String dataAccessId;

    @ApiModelProperty(value = "File")
    private Integer file;

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    private String ensembleId;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "keywords")
    private String keywords;

}

