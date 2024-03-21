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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：lnc RNA（长非编码核糖核酸）(LncRna)表实体类
 */
@Data
@ApiModel(value = "lnc RNA（长非编码核糖核酸）(LncRna)实体对象")
@TableName("in_lnc_rna")
public class LncRna extends BaseEntity {

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
     * Category（类别）
     */
    @ApiModelProperty(value = "Category（类别）")
    @TableField(value = "categories")
    private String categories;

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
     * Function（功能）
     */
    @ApiModelProperty(value = "Function（功能）")
    @TableField(value = "function")
    private String function;

    /**
     * Description（描述）
     */
    @ApiModelProperty(value = "Description（描述）")
    @TableField(value = "description")
    private String description;

    /**
     * Chr
     */
    @ApiModelProperty(value = "Chr")
    @TableField(value = "chr")
    private String chr;

    /**
     * Start（开始）
     */
    @ApiModelProperty(value = "Start（开始）")
    @TableField(value = "start")
    private String start;

    /**
     * End（结束）
     */
    @ApiModelProperty(value = "End（结束）")
    @TableField(value = "end")
    private String end;

    /**
     * Strand（链）
     */
    @ApiModelProperty(value = "Strand（链）")
    @TableField(value = "strand")
    private String strand;

    @ApiModelProperty(value = "PMID")
    @TableField(value = "pmid")
    private String pmid;

    @ApiModelProperty(value = "PMID跳转URL")
    @TableField(value = "pmid_url")
    private String pmidUrl;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

