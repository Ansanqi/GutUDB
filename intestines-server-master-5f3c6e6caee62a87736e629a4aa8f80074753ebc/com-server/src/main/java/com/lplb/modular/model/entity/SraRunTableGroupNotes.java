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
 * @Date（日期）： 2023-08-29 09:24:09
 * @Description（描述）：SraRunTable分组Note(SraRunTableGroupNotes)表实体类
 */
@Data
@ApiModel(value = "SraRunTable分组Note(SraRunTableGroupNotes)实体对象")
@TableName("in_sra_run_table_group_notes")
public class SraRunTableGroupNotes extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件编号
     */
    @ApiModelProperty(value = "文件编号")
    @TableField(value = "data_access_id")
    private String dataAccessId;

    /**
     * Note
     */
    @ApiModelProperty(value = "Note")
    @TableField(value = "notes")
    private String notes;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

