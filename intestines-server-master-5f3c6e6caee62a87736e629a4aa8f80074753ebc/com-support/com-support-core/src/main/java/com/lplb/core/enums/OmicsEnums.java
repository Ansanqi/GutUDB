package com.lplb.core.enums;

import lombok.Getter;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-28 17:51
 * @Description（描述）：OmicsConstant
 */
@Getter
public enum OmicsEnums {

    EPIGENOMICS(1684858806089605122L, "Epigenomics", "表观基因组学（Epigenomics）"),
    GENOMICS(1684858985089916930L, "Genomics", "基因组学（Genomics）"),
    TRANSCRIPTOMIC(1684859127608172546L, "Transcriptomic", "转录组学（Transcriptomic）"),
    SPATIALOMICS(1684859208558239745L, "Spatialomics", "空间组学（Spatialomics）"),
    SINGLECELLOMICS(1684859384354103298L, "Singlecellomics", "单细胞组学（Singlecellomics）"),
    PROTOEOMICS(1684859515220582402L, "Proteomics", "蛋白质组学（Proteomics）"),
    MICROBIOMICS(1684859599173771266L, "Microbiomics", "微生物组学（Microbiomics）"),
    METABOLOMICS(1684859692723527682L, "Metabolomics", "代谢组学（Metabolomics）");

    private final Long id;

    private final String name;

    private final String desc;

    OmicsEnums(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
