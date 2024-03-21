package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Proteomics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Proteomics（蛋白质组学）(Proteomics)表数据库访问层
 */
public interface ProteomicsMapper extends BaseMapper<Proteomics> {

    /**
     * Protein数据统计
     *
     * @return
     */
    List<StatisticsVo> proteinStatistics();
}

