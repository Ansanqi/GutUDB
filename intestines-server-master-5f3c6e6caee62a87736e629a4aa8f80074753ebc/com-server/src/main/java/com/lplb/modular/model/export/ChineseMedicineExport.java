package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表实体类
 */
@Data
public class ChineseMedicineExport {

    /**
     * Disease（疾病）
     */
    @ExcelProperty("Disease")
    private String disease;

    /**
     * Disease related genes（疾病相关基因）
     */
    @ExcelProperty("Disease related genes")
    private String diseaseRelatedGenes;

    /**
     * Herbs Associated with This Disease（与本病相关的草药）
     */
    @ExcelProperty("Herbs Associated with This Disease")
    private String herbsAssociatedWithThisDisease;

    /**
     * herbs_associated_with_this_disease_url
     */
    @ExcelProperty("link")
    private String herbsAssociatedWithThisDiseaseUrl;

}

