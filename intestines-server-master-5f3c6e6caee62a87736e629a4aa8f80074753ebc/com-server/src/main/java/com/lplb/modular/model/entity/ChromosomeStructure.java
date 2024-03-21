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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chromosome structure（染色体结构）(ChromosomeStructure)表实体类
 */
@Data
@ApiModel(value = "Chromosome structure（染色体结构）(ChromosomeStructure)实体对象")
@TableName("in_chromosome_structure")
public class ChromosomeStructure extends BaseEntity {

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
     * Filename
     */
    @ApiModelProperty(value = "Filename")
    @TableField(value = "file_name")
    private String fileName;

    /**
     * （Hi-C Dataset Title）标题
     */
    @ApiModelProperty(value = "（Hi-C Dataset Title）标题")
    @TableField(value = "hi_c_dataset_title")
    private String hiCDatasetTitle;

    /**
     * （3D Structure）3D结构
     */
    @ApiModelProperty(value = "（3D Structure）3D结构")
    @TableField(value = "structure_3d")
    private String structure3d;

    /**
     * 3D结构跳转地址
     */
    @ApiModelProperty(value = "3D结构跳转地址")
    @TableField(value = "structure_3d_url")
    private String structure3dUrl;

    /**
     * （Organism）生物
     */
    @ApiModelProperty(value = "（Organism）生物")
    @TableField(value = "organism")
    private String organism;

    /**
     * （GSDB ID）全球数据库ID
     */
    @ApiModelProperty(value = "（GSDB ID）全球数据库ID")
    @TableField(value = "gsdb_id")
    private String gsdbId;

    /**
     * （Project）项目
     */
    @ApiModelProperty(value = "（Project）项目")
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

