package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ColonDisease;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Colon Disease（肠道疾病）(ColonDisease)表服务接口
 */
public interface ColonDiseaseService extends IService<ColonDisease> {

    /**
     * 新增
     *
     * @param colonDisease
     * @return
     */
    Boolean insert(ColonDisease colonDisease);
}

