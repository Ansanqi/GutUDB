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
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表实体类
 */
@Data
@ApiModel(value = "HomoSapiens_NGSm6A(HomoSapiensNgsm6a)实体对象")
@TableName("in_homo_sapiens_ngsm6a")
public class HomoSapiensNgsm6a extends BaseEntity {

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
     * Number（编号）
     */
    @ApiModelProperty(value = "Number（编号）")
    @TableField(value = "number")
    private Integer number;

    /**
     * Seqnames（序号）
     */
    @ApiModelProperty(value = "Seqnames（序号）")
    @TableField(value = "seqnames")
    private String seqnames;

    /**
     * Modification（改变）
     */
    @ApiModelProperty(value = "Modification（改变）")
    @TableField(value = "modification")
    private String modification;

    /**
     * Cell（细胞）
     */
    @ApiModelProperty(value = "Cell（细胞）")
    @TableField(value = "cell")
    private String cell;

    /**
     * Condition（环境）
     */
    @ApiModelProperty(value = "Condition（环境）")
    @TableField(value = "conditions")
    private String conditions;

    /**
     * Start（开始）
     */
    @ApiModelProperty(value = "Start（开始）")
    @TableField(value = "start")
    private String start;

    /**
     * End（结束）
     */
    @ApiModelProperty(value = "End（结束）")
    @TableField(value = "end")
    private String end;

    /**
     * Width（宽度）
     */
    @ApiModelProperty(value = "Width（宽度）")
    @TableField(value = "width")
    private String width;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    @TableField(value = "strand")
    private String strand;

    /**
     * Technique（技术）
     */
    @ApiModelProperty(value = "Technique（技术）")
    @TableField(value = "technique")
    private String technique;

    /**
     * Resolution（解决）
     */
    @ApiModelProperty(value = "Resolution（解决）")
    @TableField(value = "resolution")
    private String resolution;

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
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

