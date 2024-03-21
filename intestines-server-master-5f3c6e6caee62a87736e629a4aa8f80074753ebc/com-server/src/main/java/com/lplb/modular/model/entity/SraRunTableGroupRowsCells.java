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
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列单元）(SraRunTableGroupRowsCells)表实体类
 */
@Data
@ApiModel(value = "SraRunTable分组（Sra运行表分组列单元）(SraRunTableGroupRowsCells)实体对象")
@TableName("in_sra_run_table_group_rows_cells")
public class SraRunTableGroupRowsCells extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * row列Id
     */
    @ApiModelProperty(value = "row列Id")
    @TableField(value = "rows_id")
    private Long rowsId;

    /**
     * 单元名称
     */
    @ApiModelProperty(value = "单元名称")
    @TableField(value = "name")
    private String name;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

