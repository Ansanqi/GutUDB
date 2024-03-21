package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.OmicsMapper;
import com.lplb.modular.model.entity.Omics;
import com.lplb.modular.service.OmicsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：组学(Omics)表服务实现类
 */
@Service
@Transactional
public class OmicsServiceImpl extends ServiceImpl<OmicsMapper, Omics> implements OmicsService {

    /**
     * 新增组学
     *
     * @param omics
     * @return
     */
    @Override
    public Boolean insert(Omics omics) {
        return this.save(omics);
    }
}

