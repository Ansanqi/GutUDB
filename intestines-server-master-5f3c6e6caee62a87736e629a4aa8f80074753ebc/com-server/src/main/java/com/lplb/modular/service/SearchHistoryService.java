package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.RbpAndTfList;
import com.lplb.modular.model.entity.SearchHistory;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 14:55:54
 * @Description（描述）：搜索记录(SearchHistory)表服务接口
 */
public interface SearchHistoryService extends IService<SearchHistory> {

    /**
     * 热搜
     *
     * @return
     */
    List<SearchHistory> hotSearch();

    /**
     * 搜索
     *
     * @param searchText
     * @return
     */
    RbpAndTfList search(String searchText);
}

