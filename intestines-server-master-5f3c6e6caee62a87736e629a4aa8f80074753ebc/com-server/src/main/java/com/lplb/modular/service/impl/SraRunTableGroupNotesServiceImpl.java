package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SraRunTableGroupNotesMapper;
import com.lplb.modular.model.entity.SraRunTableGroupNotes;
import com.lplb.modular.service.SraRunTableGroupNotesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-29 09:24:10
 * @Description（描述）：SraRunTable分组Note(SraRunTableGroupNotes)表服务实现类
 */
@Service
public class SraRunTableGroupNotesServiceImpl extends ServiceImpl<SraRunTableGroupNotesMapper, SraRunTableGroupNotes> implements SraRunTableGroupNotesService {

    /**
     * 根据查询groupNotes表数据
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public List<SraRunTableGroupNotes> findByDataAccessId(String dataAccessId) {
        LambdaQueryWrapper<SraRunTableGroupNotes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SraRunTableGroupNotes::getDataAccessId, dataAccessId);
        return this.list(wrapper);
    }
}

