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
 * @Date（日期）： 2023-07-29 17:34:39
 * @Description（描述）：Project（项目）(Project)表实体类
 */
@Data
@ApiModel(value = "Project（项目）(Project)实体对象")
@TableName("in_project")
public class Project extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目编码
     */
    @ApiModelProperty(value = "项目编码")
    @TableField(value = "project_no")
    private String projectNo;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    /**
     * 出现频次
     */
    @ApiModelProperty(value = "出现频次")
    @TableField(value = "frequency")
    private Integer frequency;

    /**
     * 跳转地址
     */
    @ApiModelProperty(value = "跳转地址")
    @TableField(value = "jump_url")
    private String jumpUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

