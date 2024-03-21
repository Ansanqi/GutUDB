package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：GEO_info（高通量基因表达信息）(GeoInfo)表实体类
 */
@Data
@ApiModel(value = "GEO_info（高通量基因表达信息）(GeoInfo)导出实体对象")
public class GeoInfoExport implements Serializable {

    /**
     * Data Access ID（数据ID）
     */
    @ExcelProperty("Data Access ID")
    private String dataAccessId;

    /**
     * Group（组）
     */
    @ExcelProperty("Group")
    private String groups;

    /**
     * Information（信息）
     */
    @ExcelProperty("Information")
    private String information;

    /**
     * Condition（环境）
     */
    @ExcelProperty("Condition")
    private String conditions;

    /**
     * Cell line/Tissue/Organiod（细胞系/组织/有机物）
     */
    @ExcelProperty("Cell line/Tissue/Organiod")
    private String cellLineTissueOrganiod;

    /**
     * Tissue（组织）
     */
    @ExcelProperty("Tissue")
    private String tissue;

    /**
     * Type Category（类型类别）
     */
    @ExcelProperty("Type Category")
    private String typeCategory;

    /**
     * Cell Type（细胞类型）
     */
    @ExcelProperty("Cell Type")
    private String cellType;

    /**
     * Body Site（身体部位）
     */
    @ExcelProperty("Body Site")
    private String bodyTite;

    /**
     * 文件编号
     */
    @ExcelProperty("File")
    private Integer fileNo;

    @ExcelProperty("Run")
    private String run;

}

