package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.ChineseMedicine;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表数据库访问层
 */
public interface ChineseMedicineMapper extends BaseMapper<ChineseMedicine> {

    /**
     * Chinese Medicine数据统计
     *
     * @return
     */
    List<StatisticsVo> chineseMedicineStatistics();
}

