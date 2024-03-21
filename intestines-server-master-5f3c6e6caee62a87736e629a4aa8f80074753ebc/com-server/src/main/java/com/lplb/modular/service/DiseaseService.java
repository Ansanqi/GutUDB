package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Disease;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:42:38
 * @Description（描述）：疾病(Disease)表服务接口
 */
public interface DiseaseService extends IService<Disease> {

    /**
     * 通过编号查询疾病信息
     *
     * @param diseaseNo
     * @return
     */
    Disease getByDiseaseNo(String diseaseNo);

    /**
     * 保存疾病和疾病频次信息
     *
     * @param diseaseNo
     * @param diseaseName
     * @param omicsId
     * @param className
     * @param tableName
     * @param filePath
     * @param fileName
     * @param dataId
     * @return
     */
    Boolean savaDiseaseAndSeq(String diseaseNo, String diseaseName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId);
}

