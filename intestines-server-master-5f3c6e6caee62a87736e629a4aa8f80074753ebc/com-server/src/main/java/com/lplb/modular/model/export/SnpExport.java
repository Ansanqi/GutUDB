package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SNP(Snp)表实体类
 */
@Data
public class SnpExport implements Serializable {

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
     * Name（名称）
     */
    @ExcelProperty("Name")
    private String name;

    /**
     * GRCh38Chromosome（GRCh38染色体）
     */
    @ExcelProperty("GRCh38Chromosome")
    private String grch38Chromosome;

    /**
     * GRCh38 Location（GRCh38位置）
     */
    @ExcelProperty("GRCh38 Location")
    private String grch38Location;

    /**
     * Condition（环境）
     */
    @ExcelProperty("Condition")
    private String conditions;

    /**
     * Clinical Significance（临床显着性）
     */
    @ExcelProperty("Clinical Significance")
    private String clinicalSignificance;

    /**
     * Review Status（审查状态）
     */
    @ExcelProperty("Review Status")
    private String reviewStatus;

    /**
     * Protein Change（蛋白质变化）
     */
    @ExcelProperty("Protein Change")
    private String proteinChange;

    /**
     * Accession（书目）
     */
    @ExcelProperty("Accession")
    private String accession;


}

