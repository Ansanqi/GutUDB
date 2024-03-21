package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：circRNA（环核糖核酸）(CircRna)表实体类
 */
@Data
public class CircRnaExport {

    /**
     * /**
     * Category（类别）
     */
    @ExcelProperty("Category")
    private String categories;

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
     * Species（种）
     */
    @ExcelProperty("Species")
    private String species;

    /**
     * Sample（样本）
     */
    @ExcelProperty("Sample")
    private String sample;

    /**
     * Dysfunction Pattern（机能障碍）
     */
    @ExcelProperty("Dysfunction Pattern")
    private String dysfunctionPattern;

    /**
     * Validated Method（验证方法）
     */
    @ExcelProperty("Validated Method")
    private String validatedMethod;

    /**
     * Description（描述）
     */
    @ExcelProperty("Description")
    private String description;

    @ExcelProperty("PMID")
    private String pmid;

    @ExcelProperty("pmidUrl")
    private String pmidUrl;

}

