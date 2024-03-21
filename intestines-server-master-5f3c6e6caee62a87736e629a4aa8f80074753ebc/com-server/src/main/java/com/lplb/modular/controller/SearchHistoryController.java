package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.RbpAndTfList;
import com.lplb.modular.model.entity.SearchHistory;
import com.lplb.modular.service.SearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 14:55:54
 * @Description（描述）：搜索记录(SearchHistory)表控制层
 */
@RestController
@RequestMapping("/web/searchHistory")
@Api(value = "SearchHistoryController", tags = "搜索记录(SearchHistory)")
public class SearchHistoryController {
    @Resource
    private SearchHistoryService searchHistoryService;

    @GetMapping(value = "/hotSearch")
    @ApiOperation(value = "热搜")
    public ResponseData<List<SearchHistory>> hotSearch() {
        List<SearchHistory> result = searchHistoryService.hotSearch();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/search")
    @ApiOperation(value = "搜索")
    public ResponseData<RbpAndTfList> search(@RequestParam(value = "searchText") String searchText) {
        RbpAndTfList result = searchHistoryService.search(searchText);
        return ResponseData.success(result);
    }
}

