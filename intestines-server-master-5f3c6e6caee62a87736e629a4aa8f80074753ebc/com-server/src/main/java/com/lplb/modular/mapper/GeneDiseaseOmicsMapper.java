package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.GeneDiseaseOmics;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表数据库访问层
 */
public interface GeneDiseaseOmicsMapper extends BaseMapper<GeneDiseaseOmics> {

    /**
     * 肠道疾病统计
     *
     * @return
     */
    List<StatisticsVo> intestinalDiseases();
}

