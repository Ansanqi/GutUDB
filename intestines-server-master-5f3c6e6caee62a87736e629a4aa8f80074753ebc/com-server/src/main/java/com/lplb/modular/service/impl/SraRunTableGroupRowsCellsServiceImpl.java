package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SraRunTableGroupRowsCellsMapper;
import com.lplb.modular.model.entity.SraRunTableGroupRowsCells;
import com.lplb.modular.service.SraRunTableGroupRowsCellsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列单元）(SraRunTableGroupRowsCells)表服务实现类
 */
@Service
@Transactional
public class SraRunTableGroupRowsCellsServiceImpl extends ServiceImpl<SraRunTableGroupRowsCellsMapper, SraRunTableGroupRowsCells> implements SraRunTableGroupRowsCellsService {

    /**
     * 分组单元数据保存
     *
     * @param rowId
     * @param item
     * @return
     */
    @Override
    public Boolean saveGroupCells(Long rowId, String item) {
        SraRunTableGroupRowsCells cell = new SraRunTableGroupRowsCells();
        cell.setRowsId(rowId);
        cell.setName(item);
        return this.save(cell);
    }
}

