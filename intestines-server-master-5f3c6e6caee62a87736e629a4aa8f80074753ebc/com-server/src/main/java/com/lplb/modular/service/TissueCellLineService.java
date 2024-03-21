package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.TissueCellLine;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:27:10
 * @Description（描述）：Tissue/Cell Line（组织/细胞系）(TissueCellLine)表服务接口
 */
public interface TissueCellLineService extends IService<TissueCellLine> {

    /**
     * 根据编号查询组织细胞系
     *
     * @param tissueNo
     * @return
     */
    TissueCellLine getByNo(String tissueNo);

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
    Boolean savaTissueAndSeq(String tissueNo, String tissueName, Long omicsId, String className, String tableName, String filePath, String fileName, Long dataId);
}

