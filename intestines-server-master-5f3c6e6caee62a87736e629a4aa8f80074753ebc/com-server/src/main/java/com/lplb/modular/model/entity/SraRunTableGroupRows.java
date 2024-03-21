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
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)表实体类
 */
@Data
@ApiModel(value = "SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)实体对象")
@TableName("in_sra_run_table_group_rows")
public class SraRunTableGroupRows extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    @TableField(value = "omics_id")
    private Long omicsId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * sra运行表ID
     */
    @ApiModelProperty(value = "sra运行表ID")
    @TableField(value = "run_id")
    private Long runId;

    /**
     * row列名称
     */
    @ApiModelProperty(value = "row列名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "文件编号")
    @TableField(value = "data_access_id")
    private String dataAccessId;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

