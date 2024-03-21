package com.lplb.modular.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表实体类
 */
@Data
@ApiModel(value = "项目详情实体对象")
public class ProjectDetailQuery implements Serializable {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 信息类型（Homo sapiens/Mus musculus/Other）
     */
    @ApiModelProperty(value = "信息类型（Homo sapiens/Mus musculus/Other）")
    private String infoType;

    /**
     * Project（项目）
     */
    @ApiModelProperty(value = "Project（项目）")
    private String project;

    /**
     * 项目跳转地址
     */
    @ApiModelProperty(value = "项目跳转地址")
    private String projectUrl;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    private String disease;

    /**
     * Species（种）
     */
    @ApiModelProperty(value = "Species（种）")
    private String species;

    /**
     * Body site（身体部位）
     */
    @ApiModelProperty(value = "Body site（身体部位）")
    private String bodySite;

    /**
     * CASE（情况）
     */
    @ApiModelProperty(value = "CASES（情况）")
    private String cases;

    /**
     * Control（对照）
     */
    @ApiModelProperty(value = "Control（对照）")
    private String control;

    @ApiModelProperty(value = "PMID")
    private String pmid;

    @ApiModelProperty(value = "PMID跳转URL")
    private String pmidUrl;

    /**
     * GEO Association（关联）
     */
    @ApiModelProperty(value = "GEO Association（关联）")
    private String geoAssociation;

}

