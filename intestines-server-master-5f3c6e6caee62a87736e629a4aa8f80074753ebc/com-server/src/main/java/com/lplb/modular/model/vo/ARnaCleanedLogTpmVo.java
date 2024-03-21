package com.lplb.modular.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表实体类
 */
@Data
@ApiModel(value = "A RNA cleaned log TPM(ARnaCleanedLogTpm)实体对象")
public class ARnaCleanedLogTpmVo implements Serializable {

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
     * gene_name（基因名称）
     */
    @ApiModelProperty(value = "gene_name（基因名称）")
    private String geneName;

    /**
     * Adrenal_Gland（肾上腺）
     */
    @ApiModelProperty(value = "Adrenal_Gland（肾上腺）")
    private String adrenalGland;

    /**
     * Spleen（脾脏）
     */
    @ApiModelProperty(value = "Spleen（脾脏）")
    private String spleen;

    /**
     * Heart_Atrial_Appendage（心脏心房附件）
     */
    @ApiModelProperty(value = "Heart_Atrial_Appendage（心脏心房附件）")
    private String heartAtrialAppendage;

    /**
     * Pancreas（胰腺）
     */
    @ApiModelProperty(value = "Pancreas（胰腺）")
    private String pancreas;

    /**
     * Artery_Aorta（主动脉）
     */
    @ApiModelProperty(value = "Artery_Aorta（主动脉）")
    private String arteryAorta;

    /**
     * Breast_Mammary_Tissue（乳腺组织）
     */
    @ApiModelProperty(value = "Breast_Mammary_Tissue（乳腺组织）")
    private String breastMammaryTissue;

    /**
     * Small_Intestine_Terminal_Ileum（小肠末端回肠）
     */
    @ApiModelProperty(value = "Small_Intestine_Terminal_Ileum（小肠末端回肠）")
    private String smallIntestineTerminalIleum;

    /**
     * Colon_Sigmoid（乙状结肠）
     */
    @ApiModelProperty(value = "Colon_Sigmoid（乙状结肠）")
    private String colonSigmoid;

    /**
     * Esophagus_Mucosa（食道粘膜）
     */
    @ApiModelProperty(value = "Esophagus_Mucosa（食道粘膜）")
    private String esophagusMucosa;

    /**
     * Esophagus_Muscularis（食管肌）
     */
    @ApiModelProperty(value = "Esophagus_Muscularis（食管肌）")
    private String esophagusMuscularis;

    /**
     * Esophagus_Gastroesophageal_Junction（食管-胃食管交界处）
     */
    @ApiModelProperty(value = "Esophagus_Gastroesophageal_Junction（食管-胃食管交界处）")
    private String esophagusGastroesophagealJunction;

    /**
     * Muscle_Skeletal（肌肉骨骼）
     */
    @ApiModelProperty(value = "Muscle_Skeletal（肌肉骨骼）")
    private String muscleSkeletal;

    /**
     * Thyroid（甲状腺）
     */
    @ApiModelProperty(value = "Thyroid（甲状腺）")
    private String thyroid;

    /**
     * Heart_Left_Ventricle（心脏左心室）
     */
    @ApiModelProperty(value = "Heart_Left_Ventricle（心脏左心室）")
    private String heartLeftVentricle;

    /**
     * Artery_Coronary（冠状动脉）
     */
    @ApiModelProperty(value = "Artery_Coronary（冠状动脉）")
    private String arteryCoronary;

    /**
     * Stomach（胃）
     */
    @ApiModelProperty(value = "Stomach（胃）")
    private String stomach;

    /**
     * Uterus（子宫）
     */
    @ApiModelProperty(value = "Uterus（子宫）")
    private String uterus;

    /**
     * Vagina（阴道）
     */
    @ApiModelProperty(value = "Vagina（阴道）")
    private String vagina;

    /**
     * Colon_Transverse（结肠横向）
     */
    @ApiModelProperty(value = "Colon_Transverse（结肠横向）")
    private String colonTransverse;

    /**
     * Skin_Not_Sun_Exposed(Suprapubic)（未暴露在阳光下的皮肤（肩胛上））
     */
    @ApiModelProperty(value = "Skin_Not_Sun_Exposed(Suprapubic)（未暴露在阳光下的皮肤（肩胛上））")
    private String skinNotSunExposedSuprapubic;

    /**
     * Nerve_Tibial（胫骨神经）
     */
    @ApiModelProperty(value = "Nerve_Tibial（胫骨神经）")
    private String nerveTibial;

    /**
     * Artery_Tibial（胫骨动脉）
     */
    @ApiModelProperty(value = "Artery_Tibial（胫骨动脉）")
    private String arteryTibial;

    /**
     * Ovary（卵巢）
     */
    @ApiModelProperty(value = "Ovary（卵巢）")
    private String ovary;

    /**
     * Liver（肝脏）
     */
    @ApiModelProperty(value = "Liver（肝脏）")
    private String liver;

    /**
     * Lung（肺）
     */
    @ApiModelProperty(value = "Lung（肺）")
    private String lung;

    /**
     * Brain_Cerebellum（大脑小脑）
     */
    @ApiModelProperty(value = "Brain_Cerebellum（大脑小脑）")
    private String brainCerebellum;

    /**
     * Brain_Cortex（大脑皮质）
     */
    @ApiModelProperty(value = "Brain_Cortex（大脑皮质）")
    private String brainCortex;

    /**
     * Skin_Sun_Exposed(Lower_leg)（皮肤暴露（小腿））
     */
    @ApiModelProperty(value = "Skin_Sun_Exposed(Lower_leg)（皮肤暴露（小腿））")
    private String skinSunExposedLowerLeg;

    /**
     * Pituitary（垂体）
     */
    @ApiModelProperty(value = "Pituitary（垂体）")
    private String pituitary;

    /**
     * Testis（睾丸）
     */
    @ApiModelProperty(value = "Testis（睾丸）")
    private String testis;

    /**
     * Prostate（前列腺）
     */
    @ApiModelProperty(value = "Prostate（前列腺）")
    private String prostate;

    /**
     * Minor_Salivary_Gland（小唾液腺）
     */
    @ApiModelProperty(value = "Minor_Salivary_Gland（小唾液腺）")
    private String minorSalivaryGland;

    @ApiModelProperty(value = "图片")
    private String imgUrl;

}

