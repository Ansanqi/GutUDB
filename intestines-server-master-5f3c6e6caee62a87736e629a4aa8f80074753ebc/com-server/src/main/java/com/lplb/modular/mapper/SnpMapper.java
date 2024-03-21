package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.Snp;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SNP(Snp)表数据库访问层
 */
public interface SnpMapper extends BaseMapper<Snp> {

    /**
     * Top 10 SNP
     *
     * @return
     */
    List<StatisticsVo> top10Snp();
}

