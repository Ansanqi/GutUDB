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
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表实体类
 */
@Data
@ApiModel(value = "Chemical compounds（化合物）(ChemicalCompounds)实体对象")
@TableName("in_chemical_compounds")
public class ChemicalCompounds extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * Disease related genes（疾病相关基因）
     */
    @ApiModelProperty(value = "Disease related genes（疾病相关基因）")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Species（种）
     */
    @ApiModelProperty(value = "Species（种）")
    @TableField(value = "species")
    private String species;

    /**
     * Direct Evidence（直接证明）
     */
    @ApiModelProperty(value = "Direct Evidence（直接证明）")
    @TableField(value = "direct_evidence")
    private String directEvidence;

    /**
     * Inference Network（推理网络）
     */
    @ApiModelProperty(value = "Inference Network（推理网络）")
    @TableField(value = "inference_network")
    private String inferenceNetwork;

    /**
     * Source（来源）
     */
    @ApiModelProperty(value = "Source（来源）")
    @TableField(value = "source")
    private String source;

    /**
     * source跳转地址
     */
    @ApiModelProperty(value = "source跳转地址")
    @TableField(value = "source_url")
    private String sourceUrl;

    /**
     * Reference Count（引用计数）
     */
    @ApiModelProperty(value = "Reference Count（引用计数）")
    @TableField(value = "reference_count")
    private String referenceCount;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

