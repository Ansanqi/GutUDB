package com.lplb.core.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-02 17:25
 * @Description（描述）：BeanConvertUtil
 */
public class BeanConvertUtil {

    /**
     * 对象转换
     *
     * @param source
     * @param newClass
     * @param <E>
     * @return
     */
    public static <E> E convert(Object source, Class<E> newClass) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        if (ObjectUtils.isEmpty(newClass)) {
            return null;
        }

        E newInstance = null;
        try {
            newInstance = newClass.newInstance();
        } catch (Exception e) {
            return null;
        }

        BeanUtils.copyProperties(source, newInstance);
        return newInstance;
    }

    /**
     * 集合转换
     *
     * @param sourceList
     * @param newClass
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<V> listConvert(List<T> sourceList, Class<V> newClass) {
        List<V> targetList = new ArrayList<>();
        if (sourceList != null) {
            sourceList.forEach(i -> targetList.add(BeanConvertUtil.convert(i, newClass)));
        }
        return targetList;
    }

    /**
     * 分页转换
     *
     * @param sourcePage
     * @param newClass
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> IPage<V> pageConvert(Page<T> sourcePage, Class<V> newClass) {
        if (sourcePage == null) {
            return null;
        }
        return sourcePage.convert(item -> BeanConvertUtil.convert(item, newClass));
    }
}
