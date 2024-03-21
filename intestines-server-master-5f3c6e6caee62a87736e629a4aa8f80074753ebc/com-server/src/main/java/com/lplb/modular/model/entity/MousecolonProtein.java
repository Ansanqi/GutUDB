package com.lplb.modular.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-11 09:42:05
 * @Description（描述）：GSM6578068_mousecolon_protein(MousecolonProtein)表实体类
 */
@Data
@ApiModel(value = "GSM6578068_mousecolon_protein(MousecolonProtein)实体对象")
@TableName("in_mousecolon_protein")
public class MousecolonProtein extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Cell phenotypic markers
     */
    @ApiModelProperty(value = "Cell phenotypic markers")
    @TableField(value = "cell_phenotypic_markers")
    private String cellPhenotypicMarkers;

    @ApiModelProperty(value = "详细信息")
    @TableField(value = "details")
    private String details;

    @ApiModelProperty(value = "排序")
    @TableField(value = "order_by")
    private Integer orderBy;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

