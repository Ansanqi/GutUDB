package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.mapper.OmicsCategoryMapper;
import com.lplb.modular.model.entity.OmicsCategory;
import com.lplb.modular.model.vo.OmicsCategoryTreeVo;
import com.lplb.modular.service.OmicsCategoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 18:15:46
 * @Description（描述）：分类表(OmicsCategory)表服务实现类
 */
@Service
@Transactional
public class OmicsCategoryServiceImpl extends ServiceImpl<OmicsCategoryMapper, OmicsCategory> implements OmicsCategoryService {

    /**
     * 新增分类
     *
     * @param omicsCategory
     * @return
     */
    @Override
    public Boolean insert(OmicsCategory omicsCategory) {
        return this.save(omicsCategory);
    }

    /**
     * 分类树
     *
     * @param parentId
     * @return
     */
    @Override
    public List<OmicsCategoryTreeVo> categoryTree(Long parentId) {
        if (ObjectUtils.isEmpty(parentId)) {
            parentId = 0L;
        }

        LambdaQueryWrapper<OmicsCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OmicsCategory::getParentId, parentId);
        List<OmicsCategory> list = this.list(wrapper);

        List<OmicsCategoryTreeVo> treeList = new ArrayList<>();
        // 循环类型转换，并添加下级分类
        list.forEach(category -> {
            OmicsCategoryTreeVo vo = BeanConvertUtil.convert(category, OmicsCategoryTreeVo.class);
            vo.setChild(this.categoryTree(category.getId()));
            treeList.add(vo);
        });

        return treeList;
    }
}

