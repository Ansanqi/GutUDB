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
 * @Date（日期）： 2023-09-07 10:39:03
 * @Description（描述）：疾病统计表(DiseaseStatistics)表实体类
 */
@Data
@ApiModel(value = "疾病统计表(DiseaseStatistics)实体对象")
@TableName("in_disease_statistics")
public class DiseaseStatistics extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Disease related genes
     */
    @ApiModelProperty(value = "Disease related genes")
    @TableField(value = "disease_related_genes")
    private String diseaseRelatedGenes;

    /**
     * Disease
     */
    @ApiModelProperty(value = "Disease")
    @TableField(value = "disease")
    private String disease;

    /**
     * Omics
     */
    @ApiModelProperty(value = "Omics")
    @TableField(value = "omics")
    private String omics;

    /**
     * Source
     */
    @ApiModelProperty(value = "Source")
    @TableField(value = "source")
    private String source;


    @ApiModelProperty(value = "genes_count")
    @TableField(value = "genes_count")
    private Integer genesCount;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

