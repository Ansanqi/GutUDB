package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.DiseaseStatistics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-07 10:39:03
 * @Description（描述）：疾病统计表(DiseaseStatistics)表数据库访问层
 */
public interface DiseaseStatisticsMapper extends BaseMapper<DiseaseStatistics> {

    /**
     * 肠道疾病统计
     *
     * @return
     */
    List<StatisticsVo> intestinalDiseases();

    /**
     * Top 10 Genes（出现频次前十基因）
     *
     * @return
     */
    List<StatisticsVo> top10Genes();
}

