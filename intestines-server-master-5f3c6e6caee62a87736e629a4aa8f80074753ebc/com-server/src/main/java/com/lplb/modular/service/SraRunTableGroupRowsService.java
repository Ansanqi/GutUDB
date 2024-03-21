package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SraRunTableGroupRows;
import com.lplb.modular.model.vo.SraRunTableGroupRowsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)表服务接口
 */
public interface SraRunTableGroupRowsService extends IService<SraRunTableGroupRows> {

    /**
     * 分组数据保存
     *
     * @param name
     * @param cells
     * @param dataAccessId
     */
    Boolean saveGroup(String name, List<Object> cells, String dataAccessId);

    /**
     * 根据dataAccessId查询分组信息
     *
     * @param dataAccessId
     * @return
     */
    List<SraRunTableGroupRowsVo> groupInfosByDataAccessId(String dataAccessId);

    /**
     * 根据dataAccessId查询列表
     *
     * @param dataAccessId
     * @return
     */
    List<SraRunTableGroupRows> listByDataAccessId(String dataAccessId);
}

