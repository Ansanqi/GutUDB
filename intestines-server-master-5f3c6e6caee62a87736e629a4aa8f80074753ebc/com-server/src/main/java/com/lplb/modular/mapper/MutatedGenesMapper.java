package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.MutatedGenes;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Mutated Genes（突变基因）(MutatedGenes)表数据库访问层
 */
public interface MutatedGenesMapper extends BaseMapper<MutatedGenes> {

    /**
     * Top 10 Mutated Genes
     *
     * @return
     */
    List<StatisticsVo> top10MutatedGenes();
}

