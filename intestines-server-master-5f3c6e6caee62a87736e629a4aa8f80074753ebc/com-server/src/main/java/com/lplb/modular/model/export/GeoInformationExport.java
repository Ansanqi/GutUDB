package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表实体类
 */
@Data
public class GeoInformationExport {

    /**
     * Project（项目）
     */
    @ExcelProperty("Project")
    private String project;

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
     * Body site（身体部位）
     */
    @ExcelProperty("Body site")
    private String tissueCellLine;

    /**
     * CASE（情况）
     */
    @ExcelProperty("CASE")
    private String cases;

    /**
     * Control（对照）
     */
    @ExcelProperty("Control")
    private String control;

    @ExcelProperty("PMID")
    private String pmid;

    /**
     * GEO Association（关联）
     */
    @ExcelProperty("GEO Association")
    private String geoAssociation;

}

