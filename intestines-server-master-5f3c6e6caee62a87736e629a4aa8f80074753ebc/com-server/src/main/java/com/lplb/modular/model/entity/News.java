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
 * @Date（日期）： 2023-08-06 17:44:35
 * @Description（描述）：新闻表(News)表实体类
 */
@Data
@ApiModel(value = "新闻表(News)实体对象")
@TableName("in_news")
public class News extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Published
     */
    @ApiModelProperty(value = "Published")
    @TableField(value = "published")
    private String published;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField(value = "content")
    private String content;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "cover_img")
    private String coverImg;


    @ApiModelProperty(value = "${column.comment}")
    @TableField(value = "details_img")
    private String detailsImg;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

