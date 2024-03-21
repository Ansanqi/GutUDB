package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Gene;

import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:41:01
 * @Description（描述）：基因(Gene)表服务接口
 */
public interface GeneService extends IService<Gene> {

    /**
     * 根据基因名称查询基因信息
     *
     * @param geneName
     * @return
     */
    Gene getByGnenName(String geneName);

    /**
     * 基因及基因频次信息保存
     *
     * @param geneName
     * @param geneNo
     * @param omicsId
     * @param className
     * @param tableName
     * @param filePath
     * @param fileName
     * @param dataId
     * @return
     */
    Boolean saveGeneAndSeq(String geneName, String geneNo, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId);

    /**
     * Top 10 Genes（出现频次前十基因）
     *
     * @return
     */
    Map<String, Integer> top10Genes();
}

