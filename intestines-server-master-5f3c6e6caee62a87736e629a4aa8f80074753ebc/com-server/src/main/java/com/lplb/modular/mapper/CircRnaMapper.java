package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.CircRna;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：circRNA（环核糖核酸）(CircRna)表数据库访问层
 */
public interface CircRnaMapper extends BaseMapper<CircRna> {

    /**
     * Top 10 circRNA
     *
     * @return
     */
    List<StatisticsVo> top10CircRna();
}

