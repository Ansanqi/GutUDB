package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.DiseaseMapper;
import com.lplb.modular.model.entity.Disease;
import com.lplb.modular.model.entity.DiseaseSeq;
import com.lplb.modular.service.DiseaseSeqService;
import com.lplb.modular.service.DiseaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:42:38
 * @Description（描述）：疾病(Disease)表服务实现类
 */
@Service
@Transactional
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

    @Resource
    private DiseaseSeqService diseaseSeqService;

    /**
     * 通过编号查询疾病信息
     *
     * @param diseaseNo
     * @return
     */
    @Override
    public Disease getByDiseaseNo(String diseaseNo) {
        LambdaQueryWrapper<Disease> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Disease::getDiseaseNo, diseaseNo);
        return this.getOne(wrapper);
    }

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
    @Override
    public Boolean savaDiseaseAndSeq(String diseaseNo, String diseaseName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId) {
        if (ObjectUtils.isEmpty(diseaseNo) || "NA".equals(diseaseNo)) {
            return false;
        }

        // 通过编号查询疾病信息
        Disease disease = this.getByDiseaseNo(diseaseNo);
        if (ObjectUtils.isEmpty(disease)) {
            disease = new Disease();
            disease.setDiseaseNo(diseaseNo);
            disease.setName(diseaseName);
            disease.setFrequency(1);
            this.save(disease);
        } else {
            disease.setFrequency(disease.getFrequency() + 1);
            this.updateById(disease);
        }

        // 保存频次信息
        DiseaseSeq seq = new DiseaseSeq();
        seq.setDiseaseId(disease.getId());
        seq.setOmicsId(omicsId);
        seq.setEntityName(className);
        seq.setTableName(tableName);
        seq.setFileUrl(filePath);
        seq.setFileName(fileName);
        seq.setDataId(dataId);
        diseaseSeqService.save(seq);

        return true;
    }
}

