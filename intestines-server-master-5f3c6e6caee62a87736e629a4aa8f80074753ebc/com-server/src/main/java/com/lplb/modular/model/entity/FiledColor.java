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
 * @Date（日期）： 2023-08-17 20:31:30
 * @Description（描述）：(FiledColor)表实体类
 */
@Data
@ApiModel(value = "(FiledColor)实体对象")
@TableName("in_filed_color")
public class FiledColor extends BaseEntity {

    @ApiModelProperty(value = "${column.comment}")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "type")
    private String type;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "filed")
    private String filed;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "value")
    private String value;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "color")
    private String color;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

