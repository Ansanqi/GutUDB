package com.lplb.modular.model.entity;

import java.math.BigDecimal;
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
 * @Description（描述）：A3SS.MATS.JCEC(A3ssMatsJece)表实体类
 */
@Data
@ApiModel(value = "A3SS.MATS.JCEC(A3ssMatsJece)实体对象")
@TableName("in_a3ss_mats_jece")
public class A3ssMatsJece extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
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
     * longExonStart_0base（长外显子起始0碱基）
     */
    @ApiModelProperty(value = "longExonStart_0base（长外显子起始0碱基）")
    @TableField(value = "long_exon_start_0_base")
    private Long longExonStart0Base;

    /**
     * longExonEnd（长外显子结束）
     */
    @ApiModelProperty(value = "longExonEnd（长外显子结束）")
    @TableField(value = "long_exon_end")
    private Long longExonEnd;

    /**
     * shortES（短ES）
     */
    @ApiModelProperty(value = "shortES（短ES）")
    @TableField(value = "short_es")
    private Long shortEs;

    /**
     * shortEE（短EE）
     */
    @ApiModelProperty(value = "shortEE（短EE）")
    @TableField(value = "short_ee")
    private Long shortEe;

    /**
     * flankingES（侧面ES）
     */
    @ApiModelProperty(value = "flankingES（侧面ES）")
    @TableField(value = "flanking_es")
    private Long flankingEs;

    /**
     * flankingEE（侧面EE）
     */
    @ApiModelProperty(value = "flankingEE（侧面EE）")
    @TableField(value = "flanking_ee")
    private Long flankingEe;

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

