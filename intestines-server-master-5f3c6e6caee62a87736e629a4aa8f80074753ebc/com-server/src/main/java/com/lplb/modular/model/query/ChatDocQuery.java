package com.lplb.modular.model.query;

import com.lplb.core.pojo.base.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc(ChatDoc)表实体类
 */
@Data
@ApiModel(value = "Chatdoc(ChatDoc)实体对象")
public class ChatDocQuery implements Serializable {

    @ApiModelProperty(value = "是否是关键字（0否1是）")
    private Integer isKeyWords;

    @ApiModelProperty(value = "查询词")
    private String searchText;

}

