package com.lplb.modular.model.vo;

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
 * @Date（日期）： 2023-07-31 18:37:09
 * @Description（描述）：Mutated Genes（突变基因）(MutatedGenes)表实体类
 */
@Data
@ApiModel(value = "Mutated Genes（突变基因）(MutatedGenes)实体对象")
public class MutatedGenesVo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    private Long omicsId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    /**
     * Gene Name（基因名称）
     */
    @ApiModelProperty(value = "Gene Name（基因名称）")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    /**
     * Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））
     */
    @ApiModelProperty(value = "Is Cancer Gene (source: OncoKB)（是否是癌症基因（肿瘤知识库））")
    private String isCancerGeneSourceOncoKb;

    /**
     * Freq（频率）
     */
    @ApiModelProperty(value = "Freq（频率）")
    private String freq;

    /**
     * PMID
     */
    @ApiModelProperty(value = "PMID")
    private String pmid;

    /**
     * PMID跳转URL
     */
    @ApiModelProperty(value = "PMID跳转URL")
    private String pmidUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;

    @ApiModelProperty(value = "颜色")
    private String color;

}

