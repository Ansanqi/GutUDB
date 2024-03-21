package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.MiRna;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：miRNA（微型核糖核酸）(MiRna)表数据库访问层
 */
public interface MiRnaMapper extends BaseMapper<MiRna> {

    /**
     * Top 10 miRNA
     *
     * @return
     */
    List<StatisticsVo> top10MiRna();
}

