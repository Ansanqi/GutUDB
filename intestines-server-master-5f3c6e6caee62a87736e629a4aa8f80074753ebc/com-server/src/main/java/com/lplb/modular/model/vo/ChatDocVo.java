package com.lplb.modular.model.vo;

import com.lplb.modular.model.entity.ChatDoc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc(ChatDoc)表实体类
 */
@Data
@ApiModel(value = "Chatdoc(ChatDoc)实体对象")
public class ChatDocVo implements Serializable {

    @ApiModelProperty(value = "关键词")
    private String keyWords;

    @ApiModelProperty(value = "ChatDoc列表")
    private List<ChatDoc> chatDocs;

}

