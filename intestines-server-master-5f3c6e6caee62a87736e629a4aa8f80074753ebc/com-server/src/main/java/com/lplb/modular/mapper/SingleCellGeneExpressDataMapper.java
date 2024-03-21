package com.lplb.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.modular.model.entity.SingleCellGeneExpressData;
import com.lplb.modular.model.vo.StatisticsVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表数据库访问层
 */
public interface SingleCellGeneExpressDataMapper extends BaseMapper<SingleCellGeneExpressData> {

    /**
     * Cluster数据统计
     *
     * @return
     */
    List<StatisticsVo> clusterStatistics();
}

