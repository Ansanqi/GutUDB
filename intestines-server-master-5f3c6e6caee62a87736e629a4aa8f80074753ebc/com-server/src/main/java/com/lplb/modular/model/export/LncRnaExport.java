package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：lnc RNA（长非编码核糖核酸）(LncRna)表实体类
 */
@Data
public class LncRnaExport {

    /**
     * Category（类别）
     */
    @ExcelProperty("Category")
    private String categories;

    /**
     * Ensemble ID（整体ID）
     */
    @ExcelProperty("Ensemble ID")
    private String ensembleId;

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
     * Function（功能）
     */
    @ExcelProperty("Function")
    private String function;

    /**
     * Description（描述）
     */
    @ExcelProperty("Description")
    private String description;

    /**
     * Chr
     */
    @ExcelProperty("Chr")
    private String chr;

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
     * Strand（链）
     */
    @ExcelProperty("Strand")
    private String strand;

    @ExcelProperty("PMID")
    private String pmid;

    @ExcelProperty("pmidUrl")
    private String pmidUrl;

}

