package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表实体类
 */
@Data
public class HomoSapiensNgsm6aExport {

    /**
     * Seqnames（序号）
     */
    @ExcelProperty("Seqnames")
    private String seqnames;

    /**
     * Modification（改变）
     */
    @ExcelProperty("Modification")
    private String modification;

    /**
     * Cell（细胞）
     */
    @ExcelProperty("Cell")
    private String cell;

    /**
     * Condition（环境）
     */
    @ExcelProperty("Condition")
    private String conditions;

    /**
     * Start（开始）
     */
    @ExcelProperty("Start")
    private String start;

    /**
     * End（结束）
     */
    @ExcelProperty("End")
    private String end;

    /**
     * Width（宽度）
     */
    @ExcelProperty("Width")
    private String width;

    /**
     * Strand（链）
     */
    @ExcelProperty("Strand")
    private String strand;

    /**
     * Technique（技术）
     */
    @ExcelProperty("Technique")
    private String technique;

    /**
     * Resolution（解决）
     */
    @ExcelProperty("Resolution")
    private String resolution;

    /**
     * Project（项目）
     */
    @ExcelProperty("Project")
    private String project;

}

