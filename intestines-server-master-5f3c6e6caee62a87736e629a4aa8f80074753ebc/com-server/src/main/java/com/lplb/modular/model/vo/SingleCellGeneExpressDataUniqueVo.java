package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表实体类
 */
@Data
@ApiModel(value = "单细胞组学基因表达数据Unique实体对象")
public class SingleCellGeneExpressDataUniqueVo implements Serializable {

    /**
     * Unique Id（唯一ID）
     */
    @ApiModelProperty(value = "Unique Id（唯一ID）")
    private String uniqueId;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
}

