package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：APA-pameta-merge(ApaPametaMerge)表实体类
 */
@Data
public class ApaPametaMergeExport {

    /**
     * Ensemble ID（整体ID）
     */
    @ExcelProperty("Ensemble ID")
    private String ensembleId;

    /**
     * Organism（生物）
     */
    @ExcelProperty("Organism")
    private String organism;

    /**
     * Tissue（组织）
     */
    @ExcelProperty("Tissue")
    private String tissue;

    /**
     * Condition（环境）
     */
    @ExcelProperty("Condition")
    private String conditions;

    /**
     * Protocol（协议）
     */
    @ExcelProperty("Protocol")
    private String protocol;

    /**
     * Chr
     */
    @ExcelProperty("Chr")
    private String chr;

    /**
     * start（开始）
     */
    @ExcelProperty("start")
    private Long start;

    /**
     * End（结束）
     */
    @ExcelProperty("End")
    private Long end;

    /**
     * Strand（链）
     */
    @ExcelProperty("Strand")
    private String strand;

    /**
     * PA Id
     */
    @ExcelProperty("PA Id")
    private String paId;

    /**
     * Project（项目）
     */
    @ExcelProperty("Project")
    private String project;

    /**
     * Sample ID（样本ID）
     */
    @ExcelProperty("Sample ID")
    private String sampleId;

}

