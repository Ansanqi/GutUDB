package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.DnaMethylation;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)表数据库访问层
 */
public interface DnaMethylationMapper extends BaseMapper<DnaMethylation> {

    /**
     * Top 10 Genes in DNA Methylation
     *
     * @return
     */
    List<StatisticsVo> top10GenesInDnaMethylation();
}

