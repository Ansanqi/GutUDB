package com.lplb.modular.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：D RNA TS score(DRnaTsScore)表实体类
 */
@Data
@ApiModel(value = "D RNA TS score(DRnaTsScore)实体对象")
public class DRnaTsScoreVo implements Serializable {

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
     * Artery_Aorta（主动脉）
     */
    @ApiModelProperty(value = "Artery_Aorta（主动脉）")
    private String arteryAorta;

    /**
     * Artery_Coronary（冠状动脉）
     */
    @ApiModelProperty(value = "Artery_Coronary（冠状动脉）")
    private String arteryCoronary;

    /**
     * Artery_Tibial（胫骨动脉）
     */
    @ApiModelProperty(value = "Artery_Tibial（胫骨动脉）")
    private String arteryTibial;

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
     * Breast（乳房）
     */
    @ApiModelProperty(value = "Breast（乳房）")
    private String breast;

    /**
     * Colon_Sigmoid（乙状结肠）
     */
    @ApiModelProperty(value = "Colon_Sigmoid（乙状结肠）")
    private String colonSigmoid;

    /**
     * Colon_Transverse（结肠横向）
     */
    @ApiModelProperty(value = "Colon_Transverse（结肠横向）")
    private String colonTransverse;

    /**
     * GE_junction（GE节点）
     */
    @ApiModelProperty(value = "GE_junction（GE节点）")
    private String geJunction;

    /**
     * Esophagus_Mucosa（食道粘膜）
     */
    @ApiModelProperty(value = "Esophagus_Mucosa（食道粘膜）")
    private String esophagusMucosa;

    /**
     * Esophagus_Muscle（食管肌）
     */
    @ApiModelProperty(value = "Esophagus_Muscle（食管肌）")
    private String esophagusMuscle;

    /**
     * Heart_Atrial（心脏心房）
     */
    @ApiModelProperty(value = "Heart_Atrial（心脏心房）")
    private String heartAtrial;

    /**
     * Heart_Ventricle（心脏心室）
     */
    @ApiModelProperty(value = "Heart_Ventricle（心脏心室）")
    private String heartVentricle;

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
     * Minor_Salivary（小唾液）
     */
    @ApiModelProperty(value = "Minor_Salivary（小唾液）")
    private String minorSalivary;

    /**
     * Muscle_Skeletal（肌肉骨骼）
     */
    @ApiModelProperty(value = "Muscle_Skeletal（肌肉骨骼）")
    private String muscleSkeletal;

    /**
     * Nerve_Tibial（胫骨神经）
     */
    @ApiModelProperty(value = "Nerve_Tibial（胫骨神经）")
    private String nerveTibial;

    /**
     * Ovary（卵巢）
     */
    @ApiModelProperty(value = "Ovary（卵巢）")
    private String ovary;

    /**
     * Pancreas（胰腺）
     */
    @ApiModelProperty(value = "Pancreas（胰腺）")
    private String pancreas;

    /**
     * Pituitary（垂体）
     */
    @ApiModelProperty(value = "Pituitary（垂体）")
    private String pituitary;

    /**
     * Prostate（前列腺）
     */
    @ApiModelProperty(value = "Prostate（前列腺）")
    private String prostate;

    /**
     * Skin_Unexpo（未暴露皮肤）
     */
    @ApiModelProperty(value = "Skin_Unexpo（未暴露皮肤）")
    private String skinUnexpo;

    /**
     * Skin_SunExpo（暴露皮肤）
     */
    @ApiModelProperty(value = "Skin_SunExpo（暴露皮肤）")
    private String skinSunexpo;

    /**
     * Small_Intestine（小肠）
     */
    @ApiModelProperty(value = "Small_Intestine（小肠）")
    private String smallIntestine;

    /**
     * Spleen（脾脏）
     */
    @ApiModelProperty(value = "Spleen（脾脏）")
    private String spleen;

    /**
     * Stomach（胃）
     */
    @ApiModelProperty(value = "Stomach（胃）")
    private String stomach;

    /**
     * Testis（睾丸）
     */
    @ApiModelProperty(value = "Testis（睾丸）")
    private String testis;

    /**
     * Thyroid（甲状腺）
     */
    @ApiModelProperty(value = "Thyroid（甲状腺）")
    private String thyroid;

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

    @ApiModelProperty(value = "图片")
    private String imgUrl;

}

