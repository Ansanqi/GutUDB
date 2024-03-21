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
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表实体类
 */
@Data
@ApiModel(value = "GEO_information(GeoInformation)实体对象")
@TableName("in_geo_information")
public class GeoInformation extends BaseEntity {

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
     * 信息类型（Homo sapiens/Mus musculus/Other）
     */
    @ApiModelProperty(value = "信息类型（Homo sapiens/Mus musculus/Other）")
    @TableField(value = "info_type")
    private String infoType;

    /**
     * Project（项目）
     */
    @ApiModelProperty(value = "Project（项目）")
    @TableField(value = "project")
    private String project;

    /**
     * 项目跳转地址
     */
    @ApiModelProperty(value = "项目跳转地址")
    @TableField(value = "project_url")
    private String projectUrl;

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
     * Body site（身体部位）
     */
    @ApiModelProperty(value = "Tissue/Cell line")
    @TableField(value = "tissue_cell_line")
    private String tissueCellLine;

    /**
     * CASE（情况）
     */
    @ApiModelProperty(value = "CASES（情况）")
    @TableField(value = "cases")
    private String cases;

    /**
     * Control（对照）
     */
    @ApiModelProperty(value = "Control（对照）")
    @TableField(value = "control")
    private String control;

    @ApiModelProperty(value = "PMID")
    @TableField(value = "PMID")
    private String pmid;

    @ApiModelProperty(value = "PMID跳转URL")
    @TableField(value = "pmid_url")
    private String pmidUrl;

    /**
     * GEO Association（关联）
     */
    @ApiModelProperty(value = "GEO Association（关联）")
    @TableField(value = "geo_association")
    private String geoAssociation;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

