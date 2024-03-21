package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SraRunTableGroupRowsCells;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列单元）(SraRunTableGroupRowsCells)表服务接口
 */
public interface SraRunTableGroupRowsCellsService extends IService<SraRunTableGroupRowsCells> {

    /**
     * 分组单元数据保存
     *
     * @param rowId
     * @param item
     * @return
     */
    Boolean saveGroupCells(Long rowId, String item);
}

