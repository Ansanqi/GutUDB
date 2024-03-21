package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.CnaGenes;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：CNA Genes（CAN基因）(CnaGenes)表数据库访问层
 */
public interface CnaGenesMapper extends BaseMapper<CnaGenes> {

    /**
     * Top 10 CNA Genes
     *
     * @return
     */
    List<StatisticsVo> top10CnaGenes();
}

