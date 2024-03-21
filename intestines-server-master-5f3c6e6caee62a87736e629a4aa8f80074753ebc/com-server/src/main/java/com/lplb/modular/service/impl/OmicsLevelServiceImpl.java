package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.OmicsLevelMapper;
import com.lplb.modular.model.entity.OmicsLevel;
import com.lplb.modular.service.OmicsLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：等级(OmicsLevel)表服务实现类
 */
@Service
@Transactional
public class OmicsLevelServiceImpl extends ServiceImpl<OmicsLevelMapper, OmicsLevel> implements OmicsLevelService {

    /**
     * 新增
     *
     * @param omicsLevel
     * @return
     */
    @Override
    public Boolean insert(OmicsLevel omicsLevel) {
        return this.save(omicsLevel);
    }
}

