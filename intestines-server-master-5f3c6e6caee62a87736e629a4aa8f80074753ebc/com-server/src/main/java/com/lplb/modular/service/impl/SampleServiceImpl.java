package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SampleMapper;
import com.lplb.modular.model.entity.Sample;
import com.lplb.modular.model.entity.SampleSeq;
import com.lplb.modular.service.SampleSeqService;
import com.lplb.modular.service.SampleService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:05:27
 * @Description（描述）：Sample（样本）(Sample)表服务实现类
 */
@Service
@Transactional
public class SampleServiceImpl extends ServiceImpl<SampleMapper, Sample> implements SampleService {

    @Resource
    private SampleSeqService sampleSeqService;

    /**
     * 根据样本编号查询样本信息
     *
     * @param sampleNo
     * @return
     */
    @Override
    public Sample getBySampleNo(String sampleNo) {
        LambdaQueryWrapper<Sample> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sample::getSampleNo, sampleNo);
        return this.getOne(wrapper);
    }

    /**
     * 样本及样品频次信息保存
     *
     * @param sampleNo
     * @param sampleName
     * @param omicsId
     * @param className
     * @param tableName
     * @param filePath
     * @param fileName
     * @param dataId
     * @return
     */
    @Override
    public synchronized Boolean saveSampleAndSeq(String sampleNo, String sampleName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId) {
        if (ObjectUtils.isEmpty(sampleNo) || "NA".equals(sampleNo)) {
            return false;
        }

        // 根据样本编号查询样本是否存在
        Sample sample = this.getBySampleNo(sampleNo);
        if (ObjectUtils.isEmpty(sample)) {
            sample = new Sample();
            sample.setName(sampleName);
            sample.setSampleNo(sampleNo);
            sample.setFrequency(1);
            this.save(sample);
        } else {
            sample.setFrequency(sample.getFrequency() + 1);
            this.updateById(sample);
        }

        // 保存样本频次信息
        SampleSeq seq = new SampleSeq();
        seq.setSampleId(sample.getId());
        seq.setOmicsId(omicsId);
        seq.setEntityName(className);
        seq.setTableName(tableName);
        seq.setFileUrl(filePath);
        seq.setFileName(fileName);
        seq.setDataId(dataId);
        sampleSeqService.save(seq);
        return true;
    }
}

