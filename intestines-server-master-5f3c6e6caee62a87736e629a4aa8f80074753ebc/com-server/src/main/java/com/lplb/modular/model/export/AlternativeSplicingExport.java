package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表实体类
 */
@Data
public class AlternativeSplicingExport {

    /**
     * Gene Name（基因名称）
     */
    @ExcelProperty("Gene Name")
    private String geneName;

    /**
     * Cluster1（群集1）
     */
    @ExcelProperty("Cluster1")
    private String cluster1;

    /**
     * Cluster2（群集2）
     */
    @ExcelProperty("Cluster2")
    private String cluster2;

    /**
     * Junction（接合点）
     */
    @ExcelProperty("Junction")
    private String junction;

    /**
     * logFC（logFC）
     */
    @ExcelProperty("logFC")
    private String logFc;

    /**
     * adj.P.Val（可调整p值）
     */
    @ExcelProperty("adj.P.Val")
    private String adjPVal;

}

