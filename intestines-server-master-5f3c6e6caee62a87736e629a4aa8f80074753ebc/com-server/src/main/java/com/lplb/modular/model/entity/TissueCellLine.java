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
 * @Date（日期）： 2023-07-31 10:27:09
 * @Description（描述）：Tissue/Cell Line（组织/细胞系）(TissueCellLine)表实体类
 */
@Data
@ApiModel(value = "Tissue/Cell Line（组织/细胞系）(TissueCellLine)实体对象")
@TableName("in_tissue_cell_line")
public class TissueCellLine extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组织编号
     */
    @ApiModelProperty(value = "组织编号")
    @TableField(value = "tissue_no")
    private String tissueNo;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    /**
     * 出现频次
     */
    @ApiModelProperty(value = "出现频次")
    @TableField(value = "frequency")
    private Integer frequency;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

