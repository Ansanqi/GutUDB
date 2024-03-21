package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import com.lplb.modular.model.entity.SraRunTableGroupRowsCells;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)表实体类
 */
@Data
@ApiModel(value = "SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)实体对象")
public class SraRunTableGroupRowsVo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * row列名称
     */
    @ApiModelProperty(value = "row列名称")
    private String name;

    @ApiModelProperty(value = "文件编号")
    private String dataAccessId;

    @ApiModelProperty(value = "子列表")
    private List<SraRunTableGroupRowsCells> cells;

}

