package com.lplb.modular.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-29 09:24:09
 * @Description（描述）：SraRunTable分组Note(SraRunTableGroupNotes)表实体类
 */
@Data
public class SraRunTableGroupNotesExport implements Serializable {
    /**
     * 文件编号
     */
    @ExcelProperty("Data Access ID")
    private String dataAccessId;

    /**
     * Note
     */
    @ExcelProperty("Note")
    private String notes;


}

