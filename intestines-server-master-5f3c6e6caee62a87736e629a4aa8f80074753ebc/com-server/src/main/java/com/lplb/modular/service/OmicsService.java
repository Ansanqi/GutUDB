package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Omics;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：组学(Omics)表服务接口
 */
public interface OmicsService extends IService<Omics> {

    /**
     * 新增组学
     *
     * @param omics
     * @return
     */
    Boolean insert(Omics omics);
}

