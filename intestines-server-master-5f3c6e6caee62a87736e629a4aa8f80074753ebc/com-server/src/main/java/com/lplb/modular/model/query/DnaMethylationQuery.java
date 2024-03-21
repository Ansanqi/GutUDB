package com.lplb.modular.model.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:31:44
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)查询对象
 */
@Data
@ApiModel(value = "methylation（DNA甲基化）(DnaMethylation)查询对象")
public class DnaMethylationQuery extends BaseQuery {

    @ApiModelProperty(value = "查询关键字")
    private String search;

    @ApiModelProperty(value = "排序：字段_descending/字段_ascending")
    private String orderBy;

    @ApiModelProperty(value = "Gene Name")
    private String geneName;

    @ApiModelProperty(value = "Ensemble ID")
    private String ensembleId;

    @ApiModelProperty(value = "Sample ID（样本ID）")
    private String sampleId;

    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    @ApiModelProperty(value = "Species（物种）")
    private String species;

    @ApiModelProperty(value = "Tissue/Cell Line（组织/细胞系)")
    private String tissueCellLine;

    @ApiModelProperty(value = "keywords")
    private String keywords;

}

