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
 * @Date（日期）： 2023-08-01 16:16:05
 * @Description（描述）：RBP and TF list表(RbpAndTfList)表实体类
 */
@Data
@ApiModel(value = "RBP and TF list表(RbpAndTfList)实体对象")
@TableName("in_rbp_and_tf_list")
public class RbpAndTfList extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Ensemble ID
     */
    @ApiModelProperty(value = "Ensemble ID")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Gene Name
     */
    @ApiModelProperty(value = "Gene Name")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Seqnames
     */
    @ApiModelProperty(value = "Seqnames")
    @TableField(value = "seqnames")
    private String seqnames;

    /**
     * Start
     */
    @ApiModelProperty(value = "Start")
    @TableField(value = "start")
    private String start;

    /**
     * End
     */
    @ApiModelProperty(value = "End")
    @TableField(value = "end")
    private String end;

    /**
     * Strand
     */
    @ApiModelProperty(value = "Strand")
    @TableField(value = "strand")
    private String strand;

    /**
     * RBP
     */
    @ApiModelProperty(value = "RBP")
    @TableField(value = "rbp")
    private String rbp;

    /**
     * m6A
     */
    @ApiModelProperty(value = "m6A")
    @TableField(value = "m6a")
    private String m6a;

    /**
     * AS
     */
    @ApiModelProperty(value = "AS")
    @TableField(value = "as_flag")
    private String asFlag;

    /**
     * RNA edting
     */
    @ApiModelProperty(value = "RNA edting")
    @TableField(value = "rna_edting")
    private String rnaEdting;

    /**
     * TF
     */
    @ApiModelProperty(value = "TF")
    @TableField(value = "tf")
    private String tf;

    /**
     * Motif
     */
    @ApiModelProperty(value = "Motif")
    @TableField(value = "motif")
    private String motif;

    /**
     * Therapeutic targets
     */
    @ApiModelProperty(value = "Therapeutic targets")
    @TableField(value = "therapeutic_targets")
    private String therapeuticTargets;

    /**
     * Drug
     */
    @ApiModelProperty(value = "Drug")
    @TableField(value = "drug")
    private String drug;

    /**
     * Profile
     */
    @ApiModelProperty(value = "Profile")
    @TableField(value = "profile")
    private String profile;

    /**
     * NCBI Association
     */
    @ApiModelProperty(value = "NCBI Association")
    @TableField(value = "ncbi_association")
    private String ncbiAssociation;

    /**
     * Genomics
     */
    @ApiModelProperty(value = "Genomics")
    @TableField(value = "genomics")
    private String genomics;

    /**
     * Metabolomics
     */
    @ApiModelProperty(value = "Metabolomics")
    @TableField(value = "metabolomics")
    private String metabolomics;

    /**
     * Proteomics
     */
    @ApiModelProperty(value = "Proteomics")
    @TableField(value = "proteomics")
    private String proteomics;

    /**
     * Single cell and spatial omics data
     */
    @ApiModelProperty(value = "Single cell and spatial omics data")
    @TableField(value = "single_cell_and_spatial_omics_data")
    private String singleCellAndSpatialOmicsData;

    /**
     * Transcriptomic
     */
    @ApiModelProperty(value = "Transcriptomic")
    @TableField(value = "transcriptomic")
    private String transcriptomic;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

