package com.lplb.modular.model.vo;

import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 18:15:45
 * @Description（描述）：分类表(OmicsCategory)表实体类
 */
@Data
@ApiModel(value = "分类表(OmicsCategory)树状实体对象")
public class OmicsCategoryTreeVo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    private Long omicsId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 上级分类
     */
    @ApiModelProperty(value = "上级分类")
    private Long parentId;

    /**
     * 是否有下级分类（0否1是）
     */
    @ApiModelProperty(value = "是否有下级分类（0否1是）")
    private Integer hasChild;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String describes;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderBy;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "子列表")
    private List<OmicsCategoryTreeVo> child;

}

