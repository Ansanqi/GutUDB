package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Sample;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:05:26
 * @Description（描述）：Sample（样本）(Sample)表服务接口
 */
public interface SampleService extends IService<Sample> {

    /**
     * 根据样本编号查询样本信息
     *
     * @param sampleNo
     * @return
     */
    Sample getBySampleNo(String sampleNo);

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
    Boolean saveSampleAndSeq(String sampleNo, String sampleName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId);
}

