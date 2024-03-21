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
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：等级(OmicsLevel)表实体类
 */
@Data
@ApiModel(value = "等级(OmicsLevel)实体对象")
@TableName("in_omics_level")
public class OmicsLevel extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    /**
     * 上级等级
     */
    @ApiModelProperty(value = "上级等级")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 是否有下级等级（0否1是）
     */
    @ApiModelProperty(value = "是否有下级等级（0否1是）")
    @TableField(value = "has_child")
    private Integer hasChild;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "describes")
    private String describes;

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

