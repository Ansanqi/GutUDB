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
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表实体类
 */
@Data
@ApiModel(value = "Alternative splicing（选择性剪接）(AlternativeSplicing)实体对象")
@TableName("in_alternative_splicing")
public class AlternativeSplicing extends BaseEntity {

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
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Cluster1（群集1）
     */
    @ApiModelProperty(value = "Cluster1（群集1）")
    @TableField(value = "cluster1")
    private String cluster1;

    /**
     * Cluster2（群集2）
     */
    @ApiModelProperty(value = "Cluster2（群集2）")
    @TableField(value = "cluster2")
    private String cluster2;

    /**
     * Junction（接合点）
     */
    @ApiModelProperty(value = "Junction（接合点）")
    @TableField(value = "junction")
    private String junction;

    /**
     * logFC（logFC）
     */
    @ApiModelProperty(value = "logFC（logFC）")
    @TableField(value = "log_fc")
    private String logFc;

    /**
     * adj.P.Val（可调整p值）
     */
    @ApiModelProperty(value = "adj.P.Val（可调整p值）")
    @TableField(value = "adj_p_val")
    private String adjPVal;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

