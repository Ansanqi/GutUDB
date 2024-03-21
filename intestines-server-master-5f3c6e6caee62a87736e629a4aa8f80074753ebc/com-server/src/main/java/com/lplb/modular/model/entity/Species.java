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
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：Species（物种）(Species)表实体类
 */
@Data
@ApiModel(value = "Species（物种）(Species)实体对象")
@TableName("in_species")
public class Species extends BaseEntity {

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
     * Body site（身体部位）
     */
    @ApiModelProperty(value = "Body site（身体部位）")
    @TableField(value = "body_site")
    private String bodySite;

    /**
     * Disease（疾病）
     */
    @ApiModelProperty(value = "Disease（疾病）")
    @TableField(value = "disease")
    private String disease;

    /**
     * Species name（物种名称）
     */
    @ApiModelProperty(value = "Species name（物种名称）")
    @TableField(value = "species_name")
    private String speciesName;

    /**
     * ifGmrepo（是否Gmrepo）
     */
    @ApiModelProperty(value = "ifGmrepo（是否Gmrepo）")
    @TableField(value = "if_gmrepo")
    private String ifGmrepo;

    /**
     * ifHmdad（是否Hmdad）
     */
    @ApiModelProperty(value = "ifHmdad（是否Hmdad）")
    @TableField(value = "if_hmdad")
    private String ifHmdad;

    /**
     * ifMvp（是否MVP）
     */
    @ApiModelProperty(value = "ifMvp（是否MVP）")
    @TableField(value = "if_mvp")
    private String ifMvp;

    /**
     * loaded_uid_num（加载UID编号）
     */
    @ApiModelProperty(value = "loaded_uid_num（加载UID编号）")
    @TableField(value = "loaded_uid_num")
    private Integer loadedUidNum;

    /**
     * mvpData（mvp数据）
     */
    @ApiModelProperty(value = "mvpData（mvp数据）")
    @TableField(value = "mvp_data")
    private String mvpData;

    /**
     * ncbi_taxon_id（国家生物信息中心分类单元ID）
     */
    @ApiModelProperty(value = "ncbi_taxon_id（国家生物信息中心分类单元ID）")
    @TableField(value = "ncbi_taxon_id")
    private String ncbiTaxonId;

    /**
     * relative_abundance_avg（相对丰度平均值）
     */
    @ApiModelProperty(value = "relative_abundance_avg（相对丰度平均值）")
    @TableField(value = "relative_abundance_avg")
    private String relativeAbundanceAvg;

    /**
     * relative_abundance_med（相对丰度中值）
     */
    @ApiModelProperty(value = "relative_abundance_med（相对丰度中值）")
    @TableField(value = "relative_abundance_med")
    private String relativeAbundanceMed;

    /**
     * relative_abundance_std
     */
    @ApiModelProperty(value = "relative_abundance_std")
    @TableField(value = "relative_abundance_std")
    private String relativeAbundanceStd;

    /**
     * relative_abundance_sum（相对丰度和）
     */
    @ApiModelProperty(value = "relative_abundance_sum（相对丰度和）")
    @TableField(value = "relative_abundance_sum")
    private String relativeAbundanceSum;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

