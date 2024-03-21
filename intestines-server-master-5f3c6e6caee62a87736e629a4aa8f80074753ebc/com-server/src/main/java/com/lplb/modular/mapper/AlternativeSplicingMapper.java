package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.AlternativeSplicing;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表数据库访问层
 */
public interface AlternativeSplicingMapper extends BaseMapper<AlternativeSplicing> {

    /**
     * Top 10 Genes for Single-cell Alternative Splicing
     *
     * @return
     */
    List<StatisticsVo> top10GenesForSingleCellAlternativeSplicing();
}

