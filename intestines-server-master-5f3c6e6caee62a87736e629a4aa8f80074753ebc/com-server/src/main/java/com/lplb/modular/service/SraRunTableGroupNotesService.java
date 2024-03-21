package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SraRunTableGroupNotes;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-29 09:24:10
 * @Description（描述）：SraRunTable分组Note(SraRunTableGroupNotes)表服务接口
 */
public interface SraRunTableGroupNotesService extends IService<SraRunTableGroupNotes> {

    /**
     * 根据查询groupNotes表数据
     *
     * @param dataAccessId
     * @return
     */
    List<SraRunTableGroupNotes> findByDataAccessId(String dataAccessId);
}

