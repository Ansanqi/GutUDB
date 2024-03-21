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
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Virulence gene（毒性基因）(VirulenceGene)表实体类
 */
@Data
@ApiModel(value = "Virulence gene（毒性基因）(VirulenceGene)实体对象")
@TableName("in_virulence_gene")
public class VirulenceGene extends BaseEntity {

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
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Location（位置）
     */
    @ApiModelProperty(value = "Location（位置）")
    @TableField(value = "location")
    private String location;

    /**
     * Phenotype（表现型）
     */
    @ApiModelProperty(value = "Phenotype（表现型）")
    @TableField(value = "phenotype")
    private String phenotype;

    /**
     * Inheritance（遗传特征）
     */
    @ApiModelProperty(value = "Inheritance（遗传特征）")
    @TableField(value = "inheritance")
    private String inheritance;

    /**
     * Phenotype Mapping Key（表现型映射键）
     */
    @ApiModelProperty(value = "Phenotype Mapping Key（表现型映射键）")
    @TableField(value = "phenotype_mapping_key")
    private String phenotypeMappingKey;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

