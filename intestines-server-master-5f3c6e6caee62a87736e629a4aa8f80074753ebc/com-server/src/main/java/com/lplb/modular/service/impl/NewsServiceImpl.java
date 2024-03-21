package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.mapper.NewsMapper;
import com.lplb.modular.model.dto.NewsDto;
import com.lplb.modular.model.entity.News;
import com.lplb.modular.model.query.NewsQuery;
import com.lplb.modular.service.NewsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 17:44:11
 * @Description（描述）：新闻表(News)表服务实现类
 */
@Service
@Transactional
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    /**
     * 首页数据列表查询
     *
     * @return
     */
    @Override
    public List<News> firstPageNews() {
        Page<News> page = new Page<>(1, 3);

        return this.page(page).getRecords();
    }

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<News> pageList(NewsQuery query) {
        // 分页
        Page<News> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        return this.page(page);
    }

    /**
     * 详情查询
     *
     * @param id
     * @return
     */
    @Override
    public News newsDetail(Long id) {
        return this.getById(id);
    }

    /**
     * 新增
     *
     * @param news
     * @return
     */
    @Override
    public Boolean saveNews(NewsDto news) {
        News entity = BeanConvertUtil.convert(news, News.class);
        return this.save(entity);
    }

    /**
     * 下一条
     *
     * @param id
     * @return
     */
    @Override
    public News nextNews(Long id) {
        // 查询当前新闻
        News cur = this.getById(id);
        if (ObjectUtils.isEmpty(cur)) {
            return null;
        }
        // 发布时间
        Date createTime = cur.getCreateTime();
        // 查询该发布时间之后的第一条数据
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.gt(News::getCreateTime, createTime);
        wrapper.orderByAsc(News::getCreateTime);

        Page<News> page = new Page<>(1, 1);
        page = this.page(page, wrapper);

        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getRecords())) {
            return null;
        }

        return this.page(page, wrapper).getRecords().get(0);
    }

    /**
     * 下一条
     *
     * @param id
     * @return
     */
    @Override
    public News previousNews(Long id) {
        // 查询当前新闻
        News cur = this.getById(id);
        if (ObjectUtils.isEmpty(cur)) {
            return null;
        }
        // 发布时间
        Date createTime = cur.getCreateTime();
        // 查询该发布时间之后的第一条数据
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(News::getCreateTime, createTime);
        wrapper.orderByDesc(News::getCreateTime);

        Page<News> page = new Page<>(1, 1);
        page = this.page(page, wrapper);

        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getRecords())) {
            return null;
        }

        return this.page(page, wrapper).getRecords().get(0);
    }
}

