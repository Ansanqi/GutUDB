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
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表实体类
 */
@Data
@ApiModel(value = "SraRunTable（Sra运行表）(SraRunTable)实体对象")
@TableName("in_sra_run_table")
public class SraRunTable extends BaseEntity {

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
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Disease Stage（疾病阶段）
     */
    @ApiModelProperty(value = "Disease Stage（疾病阶段）")
    @TableField(value = "disease_stage")
    private String diseaseStage;

    /**
     * Cell Type（细胞类型）
     */
    @ApiModelProperty(value = "Cell Type（细胞类型）")
    @TableField(value = "cel_type")
    private String celType;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    @TableField(value = "tissue")
    private String tissue;

    /**
     * Strain（系）
     */
    @ApiModelProperty(value = "Strain（系）")
    @TableField(value = "strain")
    private String strain;

    /**
     * Treatment（处理）
     */
    @ApiModelProperty(value = "Treatment（处理）")
    @TableField(value = "treatment")
    private String treatment;

    /**
     * Time（时间）
     */
    @ApiModelProperty(value = "Time（时间）")
    @TableField(value = "time")
    private String time;

    /**
     * Age（年龄）
     */
    @ApiModelProperty(value = "Age（年龄）")
    @TableField(value = "age")
    private String age;

    /**
     * Run（运行）
     */
    @ApiModelProperty(value = "Run（运行）")
    @TableField(value = "run")
    private String run;

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

