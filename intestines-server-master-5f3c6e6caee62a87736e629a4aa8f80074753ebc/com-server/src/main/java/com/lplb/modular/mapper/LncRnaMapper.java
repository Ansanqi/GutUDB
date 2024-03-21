package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.LncRna;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：lnc RNA（长非编码核糖核酸）(LncRna)表数据库访问层
 */
public interface LncRnaMapper extends BaseMapper<LncRna> {

    /**
     * Top 10 lnc RNA
     *
     * @return
     */
    List<StatisticsVo> top10LncRna();
}

