package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.OmicsLevel;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：等级(OmicsLevel)表服务接口
 */
public interface OmicsLevelService extends IService<OmicsLevel> {

    /**
     * 新增
     *
     * @param omicsLevel
     * @return
     */
    Boolean insert(OmicsLevel omicsLevel);
}

