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
 * @Date（日期）： 2023-08-07 15:05:43
 * @Description（描述）：mousecolon_RNA-GENE列表(MousecolonRnaGene)表实体类
 */
@Data
@ApiModel(value = "mousecolon_RNA-GENE列表(MousecolonRnaGene)实体对象")
@TableName("in_mousecolon_rna_gene")
public class MousecolonRnaGene extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 基因名称
     */
    @ApiModelProperty(value = "基因名称")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField(value = "order_by")
    private Integer orderBy;

    @ApiModelProperty(value = "排序")
    @TableField(value = "details")
    private String details;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

