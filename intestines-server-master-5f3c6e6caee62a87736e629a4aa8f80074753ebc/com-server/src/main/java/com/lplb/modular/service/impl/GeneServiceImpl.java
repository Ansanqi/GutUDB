package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.GeneMapper;
import com.lplb.modular.model.entity.Gene;
import com.lplb.modular.model.entity.GeneSeq;
import com.lplb.modular.service.GeneSeqService;
import com.lplb.modular.service.GeneService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:41:02
 * @Description（描述）：基因(Gene)表服务实现类
 */
@Service
@Transactional
public class GeneServiceImpl extends ServiceImpl<GeneMapper, Gene> implements GeneService {

    @Resource
    private GeneSeqService geneSeqService;

    /**
     * 根据基因名称查询基因信息
     *
     * @param geneName
     * @return
     */
    @Override
    public Gene getByGnenName(String geneName) {
        LambdaQueryWrapper<Gene> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Gene::getName, geneName);
        return this.getOne(wrapper);
    }


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
    @Override
    public synchronized Boolean saveGeneAndSeq(String geneName, String geneNo, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId) {
        if (ObjectUtils.isEmpty(geneName) || "NA".equals(geneName)) {
            return false;
        }

        // 根据基因名称查询基因信息
        Gene gene = this.getByGnenName(geneName);
        if (ObjectUtils.isEmpty(gene)) {
            gene = new Gene();
            gene.setGeneNo(geneNo);
            gene.setName(geneName);
            gene.setFrequency(1);
            this.save(gene);
        } else {
            gene.setFrequency(gene.getFrequency() + 1);
            this.updateById(gene);
        }

        // 添加出现记录
        GeneSeq seq = new GeneSeq();
        seq.setGeneId(gene.getId());
        seq.setOmicsId(omicsId);
        seq.setEntityName(className);
        seq.setTableName(tableName);
        seq.setFileUrl(filePath);
        seq.setFileName(fileName);
        seq.setDataId(dataId);
        geneSeqService.save(seq);
        return true;
    }

    /**
     * Top 10 Genes（出现频次前十基因）
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10Genes() {
        Page<Gene> page = new Page<>(1, 10);

        LambdaQueryWrapper<Gene> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Gene::getFrequency);

        List<Gene> list = this.page(page, wrapper).getRecords();
        if (ObjectUtils.isEmpty(list)) {
            return new LinkedHashMap();
        }

        // 前十基因数量
        Map<String, Integer> result = new LinkedHashMap<>();
        list.forEach(item -> {
            String key = item.getName().trim();
            Integer value = item.getFrequency();
            result.put(key, value);
        });
        return result;
    }
}

