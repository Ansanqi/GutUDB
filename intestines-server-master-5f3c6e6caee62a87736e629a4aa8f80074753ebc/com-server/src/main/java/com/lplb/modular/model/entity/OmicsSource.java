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
 * @Description（描述）：来源(OmicsSource)表实体类
 */
@Data
@ApiModel(value = "来源(OmicsSource)实体对象")
@TableName("in_omics_source")
public class OmicsSource extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级数据ID
     */
    @ApiModelProperty(value = "上级数据ID")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 上级数据类型
     */
    @ApiModelProperty(value = "上级数据类型")
    @TableField(value = "parent_type")
    private String parentType;

    /**
     * 来源ID
     */
    @ApiModelProperty(value = "来源ID")
    @TableField(value = "soure")
    private String soure;

    /**
     * 跳转地址
     */
    @ApiModelProperty(value = "跳转地址")
    @TableField(value = "source_url")
    private String sourceUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

