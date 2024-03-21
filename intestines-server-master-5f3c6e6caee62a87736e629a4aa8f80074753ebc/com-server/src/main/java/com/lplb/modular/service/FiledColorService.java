package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.FiledColor;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-17 20:16:03
 * @Description（描述）：(FiledColor)表服务接口
 */
public interface FiledColorService extends IService<FiledColor> {

    /**
     * 查询颜色
     *
     * @param type
     * @param filed
     * @param value
     * @return
     */
    String findByType(String type, String filed, String value);
}

