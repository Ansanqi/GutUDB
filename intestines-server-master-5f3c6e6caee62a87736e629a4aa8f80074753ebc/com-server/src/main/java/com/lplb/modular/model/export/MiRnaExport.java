package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：miRNA（微型核糖核酸）(MiRna)表实体类
 */
@Data
public class MiRnaExport {

    /**
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
     * Expression pattern of miRNA（miRNA的表达模式）
     */
    @ExcelProperty("Expression pattern of miRNA")
    private String expressionPatternOfMiRna;

    /**
     * Detection method for miRNA expression（miRNA表达检测方法）
     */
    @ExcelProperty("Detection method for miRNA expression")
    private String detectionMethodForMiRnaExpression;

    /**
     * Title（标题）
     */
    @ExcelProperty("Title")
    private String title;

}

