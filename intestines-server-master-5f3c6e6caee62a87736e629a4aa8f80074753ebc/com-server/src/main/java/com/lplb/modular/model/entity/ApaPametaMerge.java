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
 * @Description（描述）：APA-pameta-merge(ApaPametaMerge)表实体类
 */
@Data
@ApiModel(value = "APA-pameta-merge(ApaPametaMerge)实体对象")
@TableName("in_apa_pameta_merge")
public class ApaPametaMerge extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
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
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Organism（生物）
     */
    @ApiModelProperty(value = "Organism（生物）")
    @TableField(value = "organism")
    private String organism;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    @TableField(value = "tissue")
    private String tissue;

    /**
     * Condition（环境）
     */
    @ApiModelProperty(value = "Condition（环境）")
    @TableField(value = "conditions")
    private String conditions;

    /**
     * Protocol（协议）
     */
    @ApiModelProperty(value = "Protocol（协议）")
    @TableField(value = "protocol")
    private String protocol;

    /**
     * Chr
     */
    @ApiModelProperty(value = "Chr")
    @TableField(value = "chr")
    private String chr;

    /**
     * start（开始）
     */
    @ApiModelProperty(value = "start（开始）")
    @TableField(value = "start")
    private Long start;

    /**
     * End（结束）
     */
    @ApiModelProperty(value = "End（结束）")
    @TableField(value = "end")
    private Long end;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    @TableField(value = "strand")
    private String strand;

    /**
     * PA Id
     */
    @ApiModelProperty(value = "PA Id")
    @TableField(value = "pa_id")
    private String paId;

    /**
     * Project（项目）
     */
    @ApiModelProperty(value = "Project（项目）")
    @TableField(value = "project")
    private String project;

    /**
     * 项目跳转地址
     */
    @ApiModelProperty(value = "项目跳转地址")
    @TableField(value = "project_url")
    private String projectUrl;

    /**
     * Sample ID（样本ID）
     */
    @ApiModelProperty(value = "Sample ID（样本ID）")
    @TableField(value = "sample_id")
    private String sampleId;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

