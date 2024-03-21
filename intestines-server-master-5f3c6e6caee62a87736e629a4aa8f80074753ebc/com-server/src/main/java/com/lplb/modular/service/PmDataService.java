package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.PmData;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：文献数据库(PmData)表服务接口
 */
public interface PmDataService extends IService<PmData> {

    Boolean savePmData(Long omicsId, Long categoryId, Long parentId, String dataType, String pmid, String jumpUrl);
}

