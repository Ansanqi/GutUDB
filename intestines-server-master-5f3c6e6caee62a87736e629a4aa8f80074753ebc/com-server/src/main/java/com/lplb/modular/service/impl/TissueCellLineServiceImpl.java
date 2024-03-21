package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.TissueCellLineMapper;
import com.lplb.modular.model.entity.TissueCellLine;
import com.lplb.modular.model.entity.TissueCellLineSeq;
import com.lplb.modular.service.TissueCellLineSeqService;
import com.lplb.modular.service.TissueCellLineService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:27:11
 * @Description（描述）：Tissue/Cell Line（组织/细胞系）(TissueCellLine)表服务实现类
 */
@Service
@Transactional
public class TissueCellLineServiceImpl extends ServiceImpl<TissueCellLineMapper, TissueCellLine> implements TissueCellLineService {

    @Resource
    private TissueCellLineSeqService tissueCellLineSeqService;

    /**
     * 根据编号查询组织细胞系
     *
     * @param tissueNo
     * @return
     */
    @Override
    public TissueCellLine getByNo(String tissueNo) {
        LambdaQueryWrapper<TissueCellLine> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TissueCellLine::getTissueNo, tissueNo);
        return this.getOne(wrapper);
    }

    /**
     * 组织细胞系及频次保存
     *
     * @param tissueNo
     * @param tissueName
     * @param omicsId
     * @param className
     * @param tableName
     * @param filePath
     * @param fileName
     * @param dataId
     * @return
     */
    @Override
    public Boolean savaTissueAndSeq(String tissueNo, String tissueName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId) {
        if (ObjectUtils.isEmpty(tissueNo) || "NA".equals(tissueNo)) {
            return false;
        }

        // 查询该组织细胞系是否存在
        TissueCellLine tissueCellLine = this.getByNo(tissueNo);
        if (ObjectUtils.isEmpty(tissueCellLine)) {
            tissueCellLine = new TissueCellLine();
            tissueCellLine.setTissueNo(tissueNo);
            tissueCellLine.setName(tissueName);
            tissueCellLine.setFrequency(1);
            this.save(tissueCellLine);
        } else {
            tissueCellLine.setFrequency(tissueCellLine.getFrequency() + 1);
            this.updateById(tissueCellLine);
        }

        // 保存频次信息
        TissueCellLineSeq seq = new TissueCellLineSeq();
        seq.setTissueId(tissueCellLine.getId());
        seq.setOmicsId(omicsId);
        seq.setEntityName(className);
        seq.setTableName(tableName);
        seq.setFileUrl(filePath);
        seq.setFileName(fileName);
        seq.setDataId(dataId);
        tissueCellLineSeqService.save(seq);

        return null;
    }
}

