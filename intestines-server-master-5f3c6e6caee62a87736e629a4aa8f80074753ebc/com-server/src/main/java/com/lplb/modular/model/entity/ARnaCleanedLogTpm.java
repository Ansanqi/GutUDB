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
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表实体类
 */
@Data
@ApiModel(value = "A RNA cleaned log TPM(ARnaCleanedLogTpm)实体对象")
@TableName("in_a_rna_cleaned_log_tpm")
public class ARnaCleanedLogTpm extends BaseEntity {

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
     * Spleen（脾脏）
     */
    @ApiModelProperty(value = "Spleen（脾脏）")
    @TableField(value = "spleen")
    private String spleen;

    /**
     * Heart_Atrial_Appendage（心脏心房附件）
     */
    @ApiModelProperty(value = "Heart_Atrial_Appendage（心脏心房附件）")
    @TableField(value = "heart_atrial_appendage")
    private String heartAtrialAppendage;

    /**
     * Pancreas（胰腺）
     */
    @ApiModelProperty(value = "Pancreas（胰腺）")
    @TableField(value = "pancreas")
    private String pancreas;

    /**
     * Artery_Aorta（主动脉）
     */
    @ApiModelProperty(value = "Artery_Aorta（主动脉）")
    @TableField(value = "artery_aorta")
    private String arteryAorta;

    /**
     * Breast_Mammary_Tissue（乳腺组织）
     */
    @ApiModelProperty(value = "Breast_Mammary_Tissue（乳腺组织）")
    @TableField(value = "breast_mammary_tissue")
    private String breastMammaryTissue;

    /**
     * Small_Intestine_Terminal_Ileum（小肠末端回肠）
     */
    @ApiModelProperty(value = "Small_Intestine_Terminal_Ileum（小肠末端回肠）")
    @TableField(value = "small_intestine_terminal_ileum")
    private String smallIntestineTerminalIleum;

    /**
     * Colon_Sigmoid（乙状结肠）
     */
    @ApiModelProperty(value = "Colon_Sigmoid（乙状结肠）")
    @TableField(value = "colon_sigmoid")
    private String colonSigmoid;

    /**
     * Esophagus_Mucosa（食道粘膜）
     */
    @ApiModelProperty(value = "Esophagus_Mucosa（食道粘膜）")
    @TableField(value = "esophagus_mucosa")
    private String esophagusMucosa;

    /**
     * Esophagus_Muscularis（食管肌）
     */
    @ApiModelProperty(value = "Esophagus_Muscularis（食管肌）")
    @TableField(value = "esophagus_muscularis")
    private String esophagusMuscularis;

    /**
     * Esophagus_Gastroesophageal_Junction（食管-胃食管交界处）
     */
    @ApiModelProperty(value = "Esophagus_Gastroesophageal_Junction（食管-胃食管交界处）")
    @TableField(value = "esophagus_gastroesophageal_junction")
    private String esophagusGastroesophagealJunction;

    /**
     * Muscle_Skeletal（肌肉骨骼）
     */
    @ApiModelProperty(value = "Muscle_Skeletal（肌肉骨骼）")
    @TableField(value = "muscle_skeletal")
    private String muscleSkeletal;

    /**
     * Thyroid（甲状腺）
     */
    @ApiModelProperty(value = "Thyroid（甲状腺）")
    @TableField(value = "thyroid")
    private String thyroid;

    /**
     * Heart_Left_Ventricle（心脏左心室）
     */
    @ApiModelProperty(value = "Heart_Left_Ventricle（心脏左心室）")
    @TableField(value = "heart_left_ventricle")
    private String heartLeftVentricle;

    /**
     * Artery_Coronary（冠状动脉）
     */
    @ApiModelProperty(value = "Artery_Coronary（冠状动脉）")
    @TableField(value = "artery_coronary")
    private String arteryCoronary;

    /**
     * Stomach（胃）
     */
    @ApiModelProperty(value = "Stomach（胃）")
    @TableField(value = "stomach")
    private String stomach;

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
     * Colon_Transverse（结肠横向）
     */
    @ApiModelProperty(value = "Colon_Transverse（结肠横向）")
    @TableField(value = "colon_transverse")
    private String colonTransverse;

    /**
     * Skin_Not_Sun_Exposed(Suprapubic)（未暴露在阳光下的皮肤（肩胛上））
     */
    @ApiModelProperty(value = "Skin_Not_Sun_Exposed(Suprapubic)（未暴露在阳光下的皮肤（肩胛上））")
    @TableField(value = "skin_not_sun_exposed_suprapubic")
    private String skinNotSunExposedSuprapubic;

    /**
     * Nerve_Tibial（胫骨神经）
     */
    @ApiModelProperty(value = "Nerve_Tibial（胫骨神经）")
    @TableField(value = "nerve_tibial")
    private String nerveTibial;

    /**
     * Artery_Tibial（胫骨动脉）
     */
    @ApiModelProperty(value = "Artery_Tibial（胫骨动脉）")
    @TableField(value = "artery_tibial")
    private String arteryTibial;

    /**
     * Ovary（卵巢）
     */
    @ApiModelProperty(value = "Ovary（卵巢）")
    @TableField(value = "ovary")
    private String ovary;

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
     * Skin_Sun_Exposed(Lower_leg)（皮肤暴露（小腿））
     */
    @ApiModelProperty(value = "Skin_Sun_Exposed(Lower_leg)（皮肤暴露（小腿））")
    @TableField(value = "skin_sun_exposed_lower_leg")
    private String skinSunExposedLowerLeg;

    /**
     * Pituitary（垂体）
     */
    @ApiModelProperty(value = "Pituitary（垂体）")
    @TableField(value = "pituitary")
    private String pituitary;

    /**
     * Testis（睾丸）
     */
    @ApiModelProperty(value = "Testis（睾丸）")
    @TableField(value = "testis")
    private String testis;

    /**
     * Prostate（前列腺）
     */
    @ApiModelProperty(value = "Prostate（前列腺）")
    @TableField(value = "prostate")
    private String prostate;

    /**
     * Minor_Salivary_Gland（小唾液腺）
     */
    @ApiModelProperty(value = "Minor_Salivary_Gland（小唾液腺）")
    @TableField(value = "minor_salivary_gland")
    private String minorSalivaryGland;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

