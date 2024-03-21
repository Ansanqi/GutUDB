package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表实体类
 */
@Data
@ApiModel(value = "SraRunTable（Sra运行表）(SraRunTable)导出实体对象")
public class SraRunTableExport implements Serializable {

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Disease Stage（疾病阶段）
     */
    @ExcelProperty("Disease Stage")
    private String diseaseStage;

    /**
     * Cell Type（细胞类型）
     */
    @ExcelProperty("Cell Type")
    private String celType;

    /**
     * Tissue（组织）
     */
    @ExcelProperty("Tissue")
    private String tissue;

    /**
     * Strain（系）
     */
    @ExcelProperty("Strain")
    private String strain;

    /**
     * Treatment（处理）
     */
    @ExcelProperty("Treatment")
    private String treatment;

    /**
     * Time（时间）
     */
    @ExcelProperty("Time")
    private String time;

    /**
     * Age（年龄）
     */
    @ExcelProperty("Age")
    private String age;

    /**
     * Run（运行）
     */
    @ExcelProperty("Run")
    private String run;

}

