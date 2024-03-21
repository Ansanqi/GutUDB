package com.lplb.modular.service;

import com.lplb.modular.model.vo.GeneDetailVo;

import java.util.List;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-01 16:42
 * @Description（描述）：GeneDetailService
 */
public interface GeneDetailService {

    /**
     * 查询筛选疾病列表
     *
     * @return
     */
    List<String> diseaseList();

    /**
     * 根据基因名称查询基因详情
     *
     * @param geneName
     * @param disease
     * @return
     */
    GeneDetailVo geneDetailByGeneName(String geneName, String disease);
}
