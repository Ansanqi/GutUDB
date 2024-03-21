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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：D RNA TS score(DRnaTsScore)表实体类
 */
@Data
@ApiModel(value = "D RNA TS score(DRnaTsScore)实体对象")
@TableName("in_d_rna_ts_score")
public class DRnaTsScore extends BaseEntity {

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
     * gene_name（基因名称）
     */
    @ApiModelProperty(value = "gene_name（基因名称）")
    @TableField(value = "gene_name")
    private String geneName;

    /**
     * Adrenal_Gland（肾上腺）
     */
    @ApiModelProperty(value = "Adrenal_Gland（肾上腺）")
    @TableField(value = "adrenal_gland")
    private String adrenalGland;

    /**
     * Artery_Aorta（主动脉）
     */
    @ApiModelProperty(value = "Artery_Aorta（主动脉）")
    @TableField(value = "artery_aorta")
    private String arteryAorta;

    /**
     * Artery_Coronary（冠状动脉）
     */
    @ApiModelProperty(value = "Artery_Coronary（冠状动脉）")
    @TableField(value = "artery_coronary")
    private String arteryCoronary;

    /**
     * Artery_Tibial（胫骨动脉）
     */
    @ApiModelProperty(value = "Artery_Tibial（胫骨动脉）")
    @TableField(value = "artery_tibial")
    private String arteryTibial;

    /**
     * Brain_Cerebellum（大脑小脑）
     */
    @ApiModelProperty(value = "Brain_Cerebellum（大脑小脑）")
    @TableField(value = "brain_cerebellum")
    private String brainCerebellum;

    /**
     * Brain_Cortex（大脑皮质）
     */
    @ApiModelProperty(value = "Brain_Cortex（大脑皮质）")
    @TableField(value = "brain_cortex")
    private String brainCortex;

    /**
     * Breast（乳房）
     */
    @ApiModelProperty(value = "Breast（乳房）")
    @TableField(value = "breast")
    private String breast;

    /**
     * Colon_Sigmoid（乙状结肠）
     */
    @ApiModelProperty(value = "Colon_Sigmoid（乙状结肠）")
    @TableField(value = "colon_sigmoid")
    private String colonSigmoid;

    /**
     * Colon_Transverse（结肠横向）
     */
    @ApiModelProperty(value = "Colon_Transverse（结肠横向）")
    @TableField(value = "colon_transverse")
    private String colonTransverse;

    /**
     * GE_junction（GE节点）
     */
    @ApiModelProperty(value = "GE_junction（GE节点）")
    @TableField(value = "ge_junction")
    private String geJunction;

    /**
     * Esophagus_Mucosa（食道粘膜）
     */
    @ApiModelProperty(value = "Esophagus_Mucosa（食道粘膜）")
    @TableField(value = "esophagus_mucosa")
    private String esophagusMucosa;

    /**
     * Esophagus_Muscle（食管肌）
     */
    @ApiModelProperty(value = "Esophagus_Muscle（食管肌）")
    @TableField(value = "esophagus_muscle")
    private String esophagusMuscle;

    /**
     * Heart_Atrial（心脏心房）
     */
    @ApiModelProperty(value = "Heart_Atrial（心脏心房）")
    @TableField(value = "heart_atrial")
    private String heartAtrial;

    /**
     * Heart_Ventricle（心脏心室）
     */
    @ApiModelProperty(value = "Heart_Ventricle（心脏心室）")
    @TableField(value = "heart_ventricle")
    private String heartVentricle;

    /**
     * Liver（肝脏）
     */
    @ApiModelProperty(value = "Liver（肝脏）")
    @TableField(value = "liver")
    private String liver;

    /**
     * Lung（肺）
     */
    @ApiModelProperty(value = "Lung（肺）")
    @TableField(value = "lung")
    private String lung;

    /**
     * Minor_Salivary（小唾液）
     */
    @ApiModelProperty(value = "Minor_Salivary（小唾液）")
    @TableField(value = "minor_salivary")
    private String minorSalivary;

    /**
     * Muscle_Skeletal（肌肉骨骼）
     */
    @ApiModelProperty(value = "Muscle_Skeletal（肌肉骨骼）")
    @TableField(value = "muscle_skeletal")
    private String muscleSkeletal;

    /**
     * Nerve_Tibial（胫骨神经）
     */
    @ApiModelProperty(value = "Nerve_Tibial（胫骨神经）")
    @TableField(value = "nerve_tibial")
    private String nerveTibial;

    /**
     * Ovary（卵巢）
     */
    @ApiModelProperty(value = "Ovary（卵巢）")
    @TableField(value = "ovary")
    private String ovary;

    /**
     * Pancreas（胰腺）
     */
    @ApiModelProperty(value = "Pancreas（胰腺）")
    @TableField(value = "pancreas")
    private String pancreas;

    /**
     * Pituitary（垂体）
     */
    @ApiModelProperty(value = "Pituitary（垂体）")
    @TableField(value = "pituitary")
    private String pituitary;

    /**
     * Prostate（前列腺）
     */
    @ApiModelProperty(value = "Prostate（前列腺）")
    @TableField(value = "prostate")
    private String prostate;

    /**
     * Skin_Unexpo（未暴露皮肤）
     */
    @ApiModelProperty(value = "Skin_Unexpo（未暴露皮肤）")
    @TableField(value = "skin_unexpo")
    private String skinUnexpo;

    /**
     * Skin_SunExpo（暴露皮肤）
     */
    @ApiModelProperty(value = "Skin_SunExpo（暴露皮肤）")
    @TableField(value = "skin_sunExpo")
    private String skinSunexpo;

    /**
     * Small_Intestine（小肠）
     */
    @ApiModelProperty(value = "Small_Intestine（小肠）")
    @TableField(value = "small_intestine")
    private String smallIntestine;

    /**
     * Spleen（脾脏）
     */
    @ApiModelProperty(value = "Spleen（脾脏）")
    @TableField(value = "spleen")
    private String spleen;

    /**
     * Stomach（胃）
     */
    @ApiModelProperty(value = "Stomach（胃）")
    @TableField(value = "stomach")
    private String stomach;

    /**
     * Testis（睾丸）
     */
    @ApiModelProperty(value = "Testis（睾丸）")
    @TableField(value = "testis")
    private String testis;

    /**
     * Thyroid（甲状腺）
     */
    @ApiModelProperty(value = "Thyroid（甲状腺）")
    @TableField(value = "thyroid")
    private String thyroid;

    /**
     * Uterus（子宫）
     */
    @ApiModelProperty(value = "Uterus（子宫）")
    @TableField(value = "uterus")
    private String uterus;

    /**
     * Vagina（阴道）
     */
    @ApiModelProperty(value = "Vagina（阴道）")
    @TableField(value = "vagina")
    private String vagina;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

