package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Genera;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表数据库访问层
 */
public interface GeneraMapper extends BaseMapper<Genera> {

    /**
     * Genera数据统计
     *
     * @return
     */
    List<StatisticsVo> generaAssociatedWithIntestinalDiseases();
}

