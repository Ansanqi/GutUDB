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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：组学(Omics)表实体类
 */
@Data
@ApiModel(value = "组学(Omics)实体对象")
@TableName("in_omics")
public class Omics extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组学名称
     */
    @ApiModelProperty(value = "组学名称")
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "describes")
    private String describes;

    @ApiModelProperty(value = "排序")
    @TableField(value = "order_by")
    private Integer order_by;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

