package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表实体类
 */
@Data
@ApiModel(value = "单细胞组学基因表达数据详情(SingleCellGeneExpressData)实体对象")
public class SingleCellGeneExpressDataQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "查询关键字")
    private String keywords;

    @ApiModelProperty(value = "Unique Id（唯一ID）")
    private String uniqueId;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    @TableField(value = "ensemblye_id")
    private String ensembleId;

    @ApiModelProperty(value = "Tissue（组织）")
    private String tissue;

    @ApiModelProperty(value = "Cancer Type（癌症类型）")
    private String cancerType;

    @ApiModelProperty(value = "Treatment Type（处理类型）")
    private String treatmentType;


}

