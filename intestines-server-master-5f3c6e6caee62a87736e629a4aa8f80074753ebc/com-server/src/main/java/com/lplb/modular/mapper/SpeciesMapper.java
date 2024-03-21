package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Species;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：Species（物种）(Species)表数据库访问层
 */
public interface SpeciesMapper extends BaseMapper<Species> {

    /**
     * Species数据统计
     *
     * @return
     */
    List<StatisticsVo> generaAssociatedWithIntestinalDiseases();
}

