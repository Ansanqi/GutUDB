package com.lplb.modular.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 17:44:11
 * @Description（描述）：新闻表(News)表实体类
 */
@Data
@ApiModel(value = "新闻表(News)实体对象")
public class NewsDto implements Serializable {

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

}

