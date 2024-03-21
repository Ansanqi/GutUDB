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
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：GEO_info（高通量基因表达信息）(GeoInfo)表实体类
 */
@Data
@ApiModel(value = "GEO_info（高通量基因表达信息）(GeoInfo)实体对象")
@TableName("in_geo_info")
public class GeoInfo extends BaseEntity {

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
     * Data Access ID（数据ID）
     */
    @ApiModelProperty(value = "Data Access ID（数据ID）")
    @TableField(value = "data_access_id")
    private String dataAccessId;

    /**
     * Group（组）
     */
    @ApiModelProperty(value = "Group（组）")
    @TableField(value = "groups")
    private String groups;

    /**
     * Information（信息）
     */
    @ApiModelProperty(value = "Information（信息）")
    @TableField(value = "information")
    private String information;

    /**
     * Condition（环境）
     */
    @ApiModelProperty(value = "Condition（环境）")
    @TableField(value = "conditions")
    private String conditions;

    /**
     * Cell line/Tissue/Organiod（细胞系/组织/有机物）
     */
    @ApiModelProperty(value = "Cell line/Tissue/Organiod（细胞系/组织/有机物）")
    @TableField(value = "cell_line_tissue_organiod")
    private String cellLineTissueOrganiod;

    /**
     * Tissue（组织）
     */
    @ApiModelProperty(value = "Tissue（组织）")
    @TableField(value = "tissue")
    private String tissue;

    /**
     * Type Category（类型类别）
     */
    @ApiModelProperty(value = "Type Category（类型类别）")
    @TableField(value = "type_category")
    private String typeCategory;

    /**
     * Cell Type（细胞类型）
     */
    @ApiModelProperty(value = "Cell Type（细胞类型）")
    @TableField(value = "cell_type")
    private String cellType;

    /**
     * Body Site（身体部位）
     */
    @ApiModelProperty(value = "Body Site（身体部位）")
    @TableField(value = "body_tite")
    private String bodyTite;

    /**
     * 文件编号
     */
    @ApiModelProperty(value = "文件编号")
    @TableField(value = "file_no")
    private String fileNo;

    @ApiModelProperty(value = "Run")
    @TableField(value = "run")
    private String run;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

