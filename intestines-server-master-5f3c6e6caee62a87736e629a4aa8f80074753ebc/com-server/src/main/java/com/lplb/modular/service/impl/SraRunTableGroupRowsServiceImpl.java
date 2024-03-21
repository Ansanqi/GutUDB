package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.mapper.SraRunTableGroupRowsMapper;
import com.lplb.modular.model.entity.SraRunTableGroupRows;
import com.lplb.modular.model.entity.SraRunTableGroupRowsCells;
import com.lplb.modular.model.vo.SraRunTableGroupRowsVo;
import com.lplb.modular.service.SraRunTableGroupRowsCellsService;
import com.lplb.modular.service.SraRunTableGroupRowsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)表服务实现类
 */
@Service
@Transactional
public class SraRunTableGroupRowsServiceImpl extends ServiceImpl<SraRunTableGroupRowsMapper, SraRunTableGroupRows> implements SraRunTableGroupRowsService {

    @Resource
    private SraRunTableGroupRowsCellsService sraRunTableGroupRowsCellsService;

    /**
     * 分组数据保存
     *
     * @param name
     * @param cells
     * @param dataAccessId
     */
    @Override
    public Boolean saveGroup(String name, List<Object> cells, String dataAccessId) {
        SraRunTableGroupRows row = new SraRunTableGroupRows();
        row.setOmicsId(OmicsEnums.TRANSCRIPTOMIC.getId());
        row.setCategoryId(OmicsCategoryEnums.ALTERNATIVE_SPLICING.getId());
        row.setName(name);
        row.setDataAccessId(dataAccessId);
        this.save(row);

        // 保存列对应的单元
        cells.forEach(item -> {
            sraRunTableGroupRowsCellsService.saveGroupCells(row.getId(), (String) item);
        });
        return true;
    }

    /**
     * 根据dataAccessId查询分组信息
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public List<SraRunTableGroupRowsVo> groupInfosByDataAccessId(String dataAccessId) {
        List<SraRunTableGroupRows> list = this.listByDataAccessId(dataAccessId);

        List<SraRunTableGroupRowsVo> result = new ArrayList<>();
        list.forEach(row -> {
            SraRunTableGroupRowsVo vo = BeanConvertUtil.convert(row, SraRunTableGroupRowsVo.class);

            // 查询下级单元
            LambdaQueryWrapper<SraRunTableGroupRowsCells> cellWrapper = new LambdaQueryWrapper<>();
            cellWrapper.eq(SraRunTableGroupRowsCells::getRowsId, row.getId());
            List<SraRunTableGroupRowsCells> cells = sraRunTableGroupRowsCellsService.list(cellWrapper);

            vo.setCells(cells);
            result.add(vo);
        });
        return result;
    }

    /**
     * 根据dataAccessId查询列表
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public List<SraRunTableGroupRows> listByDataAccessId(String dataAccessId) {
        LambdaQueryWrapper<SraRunTableGroupRows> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SraRunTableGroupRows::getDataAccessId, dataAccessId);
        wrapper.orderByAsc(SraRunTableGroupRows::getName);

        return this.list(wrapper);
    }
}

