package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.dto.NewsDto;
import com.lplb.modular.model.entity.News;
import com.lplb.modular.model.query.NewsQuery;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 17:44:11
 * @Description（描述）：新闻表(News)表服务接口
 */
public interface NewsService extends IService<News> {

    /**
     * 首页数据列表查询
     *
     * @return
     */
    List<News> firstPageNews();

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    IPage<News> pageList(NewsQuery query);

    /**
     * 详情查询
     *
     * @param id
     * @return
     */
    News newsDetail(Long id);

    /**
     * 新增
     *
     * @param news
     * @return
     */
    Boolean saveNews(NewsDto news);

    /**
     * 下一条
     *
     * @param id
     * @return
     */
    News nextNews(Long id);

    /**
     * 上一条
     *
     * @param id
     * @return
     */
    News previousNews(Long id);
}

