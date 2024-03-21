package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.PmDataMapper;
import com.lplb.modular.model.entity.PmData;
import com.lplb.modular.service.PmDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：文献数据库(PmData)表服务实现类
 */
@Service
@Transactional
public class PmDataServiceImpl extends ServiceImpl<PmDataMapper, PmData> implements PmDataService {

    @Override
    public Boolean savePmData(Long omicsId, Long categoryId, Long parentId, String dataType, String pmid, String jumpUrl) {
        PmData data = new PmData();
        data.setOmicsId(omicsId);
        data.setCategoryId(categoryId);
        data.setParentId(parentId);
        data.setDataType(dataType);
        data.setPmid(pmid);
        data.setJumpUrl(jumpUrl);
        return this.save(data);
    }

}

