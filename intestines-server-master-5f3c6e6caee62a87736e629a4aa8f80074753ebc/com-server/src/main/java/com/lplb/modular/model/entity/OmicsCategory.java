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
 * @Date（日期）： 2023-07-28 18:15:45
 * @Description（描述）：分类表(OmicsCategory)表实体类
 */
@Data
@ApiModel(value = "分类表(OmicsCategory)实体对象")
@TableName("in_omics_category")
public class OmicsCategory extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    @TableField(value = "omics_id")
    private Long omicsId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "name")
    private String name;

    /**
     * 上级分类
     */
    @ApiModelProperty(value = "上级分类")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 是否有下级分类（0否1是）
     */
    @ApiModelProperty(value = "是否有下级分类（0否1是）")
    @TableField(value = "has_child")
    private Integer hasChild;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "describes")
    private String describes;

    /**
     * 排序
     */
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

