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
 * @Description（描述）：Chatdoc(ChatDoc)表实体类
 */
@Data
@ApiModel(value = "Chatdoc(ChatDoc)实体对象")
@TableName("in_chat_doc")
public class ChatDoc extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关键字ID
     */
    @ApiModelProperty(value = "关键字ID")
    @TableField(value = "key_words_id")
    private Long keyWordsId;

    /**
     * 问题编号
     */
    @ApiModelProperty(value = "问题编号")
    @TableField(value = "order_no")
    private Integer orderNo;

    /**
     * 问题
     */
    @ApiModelProperty(value = "问题")
    @TableField(value = "question")
    private String question;

    /**
     * 答案
     */
    @ApiModelProperty(value = "答案")
    @TableField(value = "answer")
    private String answer;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

