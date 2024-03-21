package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.PositionStatistics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-08 11:42:04
 * @Description（描述）：Position统计表(PositionStatistics)表数据库访问层
 */
public interface PositionStatisticsMapper extends BaseMapper<PositionStatistics> {

    /**
     * 肠道部位统计
     *
     * @return
     */
    List<StatisticsVo> position();
}

