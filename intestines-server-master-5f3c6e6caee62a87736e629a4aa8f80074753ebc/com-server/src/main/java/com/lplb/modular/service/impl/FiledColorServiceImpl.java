package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.FiledColorMapper;
import com.lplb.modular.model.entity.FiledColor;
import com.lplb.modular.service.FiledColorService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-17 20:16:03
 * @Description（描述）：(FiledColor)表服务实现类
 */
@Service
public class FiledColorServiceImpl extends ServiceImpl<FiledColorMapper, FiledColor> implements FiledColorService {

    /**
     * 查询颜色
     *
     * @param type
     * @param filed
     * @param value
     * @return
     */
    @Override
    public String findByType(String type, String filed, String value) {
        LambdaQueryWrapper<FiledColor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FiledColor::getType, type);
        wrapper.eq(FiledColor::getFiled, filed);
        wrapper.eq(FiledColor::getValue, value);

        List<FiledColor> list = this.list(wrapper);
        if (ObjectUtils.isNotEmpty(list)) {
            return list.get(0).getColor();
        } else {
            return "";
        }
    }
}

