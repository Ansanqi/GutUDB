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
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc_key_words(ChatDocKeyWords)表实体类
 */
@Data
@ApiModel(value = "Chatdoc_key_words(ChatDocKeyWords)实体对象")
@TableName("in_chat_doc_key_words")
public class ChatDocKeyWords extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * key words（关键词）
     */
    @ApiModelProperty(value = "key words（关键词）")
    @TableField(value = "key_words")
    private String keyWords;

    /**
     * 搜索次数
     */
    @ApiModelProperty(value = "搜索次数")
    @TableField(value = "search_count")
    private Integer searchCount;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

