package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Histone;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)表数据库访问层
 */
public interface HistoneMapper extends BaseMapper<Histone> {

    /**
     * Histone统计
     *
     * @return
     */
    List<StatisticsVo> histoneStatistics();
}

