package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.StructuraVariantGenes;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Structural Variant Genes（结构变异基因）(StructuraVariantGenes)表数据库访问层
 */
public interface StructuraVariantGenesMapper extends BaseMapper<StructuraVariantGenes> {

    /**
     * Top 10 Structural Variant Genes
     *
     * @return
     */
    List<StatisticsVo> top10StructuralVariantGenes();
}

