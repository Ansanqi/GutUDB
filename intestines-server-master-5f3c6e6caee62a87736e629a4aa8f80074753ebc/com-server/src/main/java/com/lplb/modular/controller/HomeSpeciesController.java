package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.HomeSpecies;
import com.lplb.modular.model.query.HomeSpeciesQuery;
import com.lplb.modular.service.HomeSpeciesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 23:02:24
 * @Description（描述）：Home | Species(HomeSpecies)表控制层
 */
@RestController
@RequestMapping("/web/homeSpecies")
@Api(value = "HomeSpeciesController", tags = "Home | Species(HomeSpecies)")
public class HomeSpeciesController {
    @Resource
    private HomeSpeciesService homeSpeciesService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = homeSpeciesService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<HomeSpecies>> page(HomeSpeciesQuery query) {
        IPage<HomeSpecies> result = homeSpeciesService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, HomeSpeciesQuery query) {
        homeSpeciesService.exportExcel(response, query);
    }
}

