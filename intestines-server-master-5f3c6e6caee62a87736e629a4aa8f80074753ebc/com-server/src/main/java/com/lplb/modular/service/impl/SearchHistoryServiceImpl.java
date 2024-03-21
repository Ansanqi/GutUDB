package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SearchHistoryMapper;
import com.lplb.modular.model.entity.RbpAndTfList;
import com.lplb.modular.model.entity.SearchHistory;
import com.lplb.modular.service.RbpAndTfListService;
import com.lplb.modular.service.SearchHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 14:55:54
 * @Description（描述）：搜索记录(SearchHistory)表服务实现类
 */
@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements SearchHistoryService {

    @Resource
    private RbpAndTfListService rbpAndTfListService;

    /**
     * 热搜
     *
     * @return
     */
    @Override
    public List<SearchHistory> hotSearch() {
        Page<SearchHistory> page = new Page<>(1, 10);
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SearchHistory::getSearchCount);
        page = this.page(page, wrapper);

        return page.getRecords();
    }

    /**
     * 搜索
     *
     * @param searchText
     * @return
     */
    @Override
    public RbpAndTfList search(String searchText) {
        if (ObjectUtils.isEmpty(searchText)) {
            return null;
        }
        // 查询是否有该词的搜索记录
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchHistory::getAboutGene, searchText);
        SearchHistory history = this.getOne(wrapper);

        // 模糊搜索基因列表
        RbpAndTfList gene = rbpAndTfListService.getByGeneName(searchText);
        if (ObjectUtils.isNotEmpty(gene)) {

            if (ObjectUtils.isNotEmpty(history)) {
                // 添加搜索次数
                history.setSearchCount(history.getSearchCount() + 1);
                this.updateById(history);
            } else {
                // 添加搜索记录
                history = new SearchHistory();
                history.setSearchText(searchText);
                history.setSearchCount(1);
                history.setAboutGene(searchText);
                this.save(history);
            }
        }

        return gene;
    }
}

