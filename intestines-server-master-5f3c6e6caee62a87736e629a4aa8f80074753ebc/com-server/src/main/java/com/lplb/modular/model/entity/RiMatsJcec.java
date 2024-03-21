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
 * @Date（日期）： 2023-07-27 18:35:43
 * @Description（描述）：RI.MATS.JCEC(RiMatsJcec)表实体类
 */
@Data
@ApiModel(value = "RI.MATS.JCEC(RiMatsJcec)实体对象")
@TableName("in_ri_mats_jcec")
public class RiMatsJcec extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * Data Access ID（数据ID）
     */
    @ApiModelProperty(value = "Data Access ID（数据ID）")
    @TableField(value = "data_access_id")
    private String dataAccessId;

    /**
     * 文件编号
     */
    @ApiModelProperty(value = "文件编号")
    @TableField(value = "file_no")
    private Integer fileNo;

    /**
     * Ensemble ID（整体ID）
     */
    @ApiModelProperty(value = "Ensemble ID（整体ID）")
    @TableField(value = "ensemble_id")
    private String ensembleId;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * chr
     */
    @ApiModelProperty(value = "chr")
    @TableField(value = "chr")
    private String chr;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    @TableField(value = "strand")
    private String strand;

    /**
     * riExonStart_0base（ri显子起始0碱基）
     */
    @ApiModelProperty(value = "riExonStart_0base（ri显子起始0碱基）")
    @TableField(value = "ri_exon_start_0_base")
    private Long riExonStart0Base;

    /**
     * riExonEnd（ri显子结束）
     */
    @ApiModelProperty(value = "riExonEnd（ri显子结束）")
    @TableField(value = "ri_exon_end")
    private Long riExonEnd;

    /**
     * upstreamES（上游ES）
     */
    @ApiModelProperty(value = "upstreamES（上游ES）")
    @TableField(value = "upstream_es")
    private Long upstreamEs;

    /**
     * upstreamEE（上游EE）
     */
    @ApiModelProperty(value = "upstreamEE（上游EE）")
    @TableField(value = "upstream_ee")
    private Long upstreamEe;

    /**
     * downstreamES（下游ES）
     */
    @ApiModelProperty(value = "downstreamES（下游ES）")
    @TableField(value = "downstream_es")
    private Long downstreamEs;

    /**
     * downstreamEE（下游EE）
     */
    @ApiModelProperty(value = "downstreamEE（下游EE）")
    @TableField(value = "downstream_ee")
    private Long downstreamEe;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableField(value = "id_no")
    private Integer idNo;

    /**
     * IJC_SAMPLE_1（IJC样本1）
     */
    @ApiModelProperty(value = "IJC_SAMPLE_1（IJC样本1）")
    @TableField(value = "ijc_sample_1")
    private String ijcSample1;

    /**
     * SJC_SAMPLE_1（SJC样本1）
     */
    @ApiModelProperty(value = "SJC_SAMPLE_1（SJC样本1）")
    @TableField(value = "sjc_sample_1")
    private String sjcSample1;

    /**
     * IJC_SAMPLE_2（IJC样本2）
     */
    @ApiModelProperty(value = "IJC_SAMPLE_2（IJC样本2）")
    @TableField(value = "ijc_sample_2")
    private String ijcSample2;

    /**
     * SJC_SAMPLE_2（SJC样本2）
     */
    @ApiModelProperty(value = "SJC_SAMPLE_2（SJC样本2）")
    @TableField(value = "sjc_sample_2")
    private String sjcSample2;

    /**
     * IncFormLen
     */
    @ApiModelProperty(value = "IncFormLen")
    @TableField(value = "inc_form_len")
    private Integer incFormLen;

    /**
     * SkipFormLen
     */
    @ApiModelProperty(value = "SkipFormLen")
    @TableField(value = "skip_form_len")
    private Integer skipFormLen;

    /**
     * Pvalue（p值）
     */
    @ApiModelProperty(value = "Pvalue（p值）")
    @TableField(value = "p_value")
    private String pValue;

    /**
     * FDR
     */
    @ApiModelProperty(value = "FDR")
    @TableField(value = "fdr")
    private String fdr;

    /**
     * IncLevel1名称
     */
    @ApiModelProperty(value = "IncLevel1名称")
    @TableField(value = "inc_level_1_name")
    private String incLevel1Name;

    /**
     * IncLevel1值
     */
    @ApiModelProperty(value = "IncLevel1值")
    @TableField(value = "inc_level_1")
    private String incLevel1;

    /**
     * IncLevel2名称
     */
    @ApiModelProperty(value = "IncLevel2名称")
    @TableField(value = "inc_level_2_name")
    private String incLevel2Name;

    /**
     * IncLevel2值
     */
    @ApiModelProperty(value = "IncLevel2值")
    @TableField(value = "inc_level_2")
    private String incLevel2;

    /**
     * IncLevelDifference
     */
    @ApiModelProperty(value = "IncLevelDifference")
    @TableField(value = "inc_level_difference")
    private String incLevelDifference;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

