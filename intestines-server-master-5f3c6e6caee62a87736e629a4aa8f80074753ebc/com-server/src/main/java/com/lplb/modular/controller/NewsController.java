package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.dto.NewsDto;
import com.lplb.modular.model.entity.News;
import com.lplb.modular.model.query.NewsQuery;
import com.lplb.modular.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 17:44:11
 * @Description（描述）：新闻表(News)表控制层
 */
@RestController
@RequestMapping("/web/news")
@Api(value = "NewsController", tags = "新闻表(News)")
public class NewsController {
    @Resource
    private NewsService newsService;

    @GetMapping(value = "/firstPageNews")
    @ApiOperation(value = "首页数据列表查询")
    public ResponseData<List<News>> firstPageNews() {
        List<News> result = newsService.firstPageNews();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/pageList")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<News>> newsList(NewsQuery query) {
        IPage<News> result = newsService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/newsDetail")
    @ApiOperation(value = "详情查询")
    public ResponseData<News> newsDetail(@RequestParam(value = "id") Long id) {
        News result = newsService.newsDetail(id);
        return ResponseData.success(result);
    }

    @PostMapping(value = "/newsAdd")
    @ApiOperation(value = "新增")
    public ResponseData<Boolean> newsAdd(@RequestBody NewsDto news) {
        Boolean result = newsService.saveNews(news);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/nextNews")
    @ApiOperation(value = "下一条")
    public ResponseData<News> nextNews(@RequestParam(value = "id") Long id) {
        News result = newsService.nextNews(id);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/previousNews")
    @ApiOperation(value = "上一条")
    public ResponseData<News> previousNews(@RequestParam(value = "id") Long id) {
        News result = newsService.previousNews(id);
        return ResponseData.success(result);
    }

}

