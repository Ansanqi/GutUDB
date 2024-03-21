package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Virulence gene（毒性基因）(VirulenceGene)导出实体类
 */
@Data
public class VirulenceGeneExport implements Serializable {

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Location（位置）
     */
    @ExcelProperty("Location")
    private String location;

    /**
     * Phenotype（表现型）
     */
    @ExcelProperty("Phenotype")
    private String phenotype;

    /**
     * Inheritance（遗传特征）
     */
    @ExcelProperty("Inheritance")
    private String inheritance;

    /**
     * Phenotype Mapping Key（表现型映射键）
     */
    @ExcelProperty("Phenotype Mapping Key")
    private String phenotypeMappingKey;

}

