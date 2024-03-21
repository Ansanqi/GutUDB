package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Metabolomics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表数据库访问层
 */
public interface MetabolomicsMapper extends BaseMapper<Metabolomics> {

    /**
     * Metabolomics数据统计
     *
     * @return
     */
    List<StatisticsVo> metaboliteStatistics();
}

