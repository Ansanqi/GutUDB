package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表实体类
 */
@Data
public class ProbioticsExport {

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Probiotics（益生菌）
     */
    @ExcelProperty("Probiotics")
    private String probiotics;

    /**
     * Genus（属）
     */
    @ExcelProperty("Genus")
    private String genus;

    /**
     * Location（位置）
     */
    @ExcelProperty("Location")
    private String location;

    /**
     * Function（功能）
     */
    @ExcelProperty("Function")
    private String function;

    /**
     * PMID
     */
    @ExcelProperty("PMID")
    private String pmid;

    /**
     * PMID跳转URL
     */
    @ExcelProperty("pmidUrl")
    private String pmidUrl;

}

