package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.OmicsCategory;
import com.lplb.modular.model.vo.OmicsCategoryTreeVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 18:15:45
 * @Description（描述）：分类表(OmicsCategory)表服务接口
 */
public interface OmicsCategoryService extends IService<OmicsCategory> {

    /**
     * 新增分类
     *
     * @param omicsCategory
     * @return
     */
    Boolean insert(OmicsCategory omicsCategory);

    /**
     * 分类树
     *
     * @param parentId
     * @return
     */
    List<OmicsCategoryTreeVo> categoryTree(Long parentId);
}

