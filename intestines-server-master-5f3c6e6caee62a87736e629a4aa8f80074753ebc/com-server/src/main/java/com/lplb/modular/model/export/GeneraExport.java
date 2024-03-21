package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表实体类
 */
@Data
public class GeneraExport {

    /**
     * Body site（身体部位）
     */
    @ExcelProperty("Body site")
    private String bodySite;

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Genera name（属群名称）
     */
    @ExcelProperty("Genera name")
    private String generaName;

    /**
     * ifGmrepo（是否Gmrepo）
     */
    @ExcelProperty("ifGmrepo")
    private String ifGmrepo;

    /**
     * ifMvp（是否MVP）
     */
    @ExcelProperty("ifMvp")
    private String ifMvp;

    /**
     * loaded_uid_num（加载UID编号）
     */
    @ExcelProperty("loaded_uid_num")
    private Integer loadedUidNum;

    /**
     * mvpData（mvp数据）
     */
    @ExcelProperty("mvpData")
    private String mvpData;

    /**
     * ncbi_taxon_id（国家生物信息中心分类单元ID）
     */
    @ExcelProperty("ncbi_taxon_id")
    private String ncbiTaxonId;

    /**
     * relative_abundance_avg（相对丰度平均值）
     */
    @ExcelProperty("relative_abundance_avg")
    private String relativeAbundanceAvg;

    /**
     * relative_abundance_med（相对丰度中值）
     */
    @ExcelProperty("relative_abundance_med")
    private String relativeAbundanceMed;

    /**
     * relative_abundance_std（相对丰度标准值）
     */
    @ExcelProperty("relative_abundance_std")
    private String relativeAbundanceStd;

    /**
     * relative_abundance_sum（相对丰度和）
     */
    @ExcelProperty("relative_abundance_sum")
    private String relativeAbundanceSum;

}

