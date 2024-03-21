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
 * @Date（日期）： 2023-08-04 23:04:30
 * @Description（描述）：Home | Species(HomeSpecies)表实体类
 */
@Data
@ApiModel(value = "Home | Species(HomeSpecies)实体对象")
@TableName("in_home_species")
public class HomeSpecies extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Species
     */
    @ApiModelProperty(value = "Species")
    @TableField(value = "species")
    private String species;

    /**
     * Disease
     */
    @ApiModelProperty(value = "Disease")
    @TableField(value = "disease")
    private String disease;

    /**
     * Disease related genes
     */
    @ApiModelProperty(value = "Disease related genes")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Direct Evidence
     */
    @ApiModelProperty(value = "Direct Evidence")
    @TableField(value = "direct_evidence")
    private String directEvidence;

    /**
     * Inference Network
     */
    @ApiModelProperty(value = "Inference Network")
    @TableField(value = "inference_network")
    private String inferenceNetwork;

    /**
     * Source
     */
    @ApiModelProperty(value = "Source")
    @TableField(value = "source")
    private String source;

    /**
     * Reference Count
     */
    @ApiModelProperty(value = "Reference Count")
    @TableField(value = "reference_count")
    private String referenceCount;

    /**
     * link
     */
    @ApiModelProperty(value = "link")
    @TableField(value = "link")
    private String link;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

