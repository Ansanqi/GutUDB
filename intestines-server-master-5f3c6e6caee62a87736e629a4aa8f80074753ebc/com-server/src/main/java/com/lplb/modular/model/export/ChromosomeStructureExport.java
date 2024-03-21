package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chromosome structure（染色体结构）(ChromosomeStructure)导出实体类
 */
@Data
public class ChromosomeStructureExport implements Serializable {

    @ExcelProperty("Filename")
    private String fileName;

    /**
     * （Hi-C Dataset Title）标题
     */
    @ExcelProperty("Hi-C Dataset Title")
    private String hiCDatasetTitle;

    /**
     * （3D Structure）3D结构
     */
    @ExcelProperty("3D Structure")
    private String structure3d;

    /**
     * 3D结构跳转地址
     */
    @ExcelProperty("Structure link")
    private String structure3dUrl;

    /**
     * （Organism）生物
     */
    @ExcelProperty("Organism")
    private String organism;

    /**
     * （GSDB ID）全球数据库ID
     */
    @ExcelProperty("GSDB ID")
    private String gsdbId;

    /**
     * （Project）项目
     */
    @ExcelProperty("Project")
    private String project;

    /**
     * 项目跳转地址
     */
    @ExcelProperty("Project link")
    private String projectUrl;

}

