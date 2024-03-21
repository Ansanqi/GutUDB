package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Probiotics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表数据库访问层
 */
public interface ProbioticsMapper extends BaseMapper<Probiotics> {

    /**
     * Probiotics统计
     *
     * @return
     */
    List<StatisticsVo> probioticsStatistics();

    /**
     * Top 10 Probiotics associated with Intestinal Diseases
     *
     * @return
     */
    List<StatisticsVo> top10ProbioticsAssociatedWithIntestinalDiseases();
}

