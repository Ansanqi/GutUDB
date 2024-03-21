package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.AlternativeSplicing;
import com.lplb.modular.model.query.AlternativeSplicingQuery;
import com.lplb.modular.service.AlternativeSplicingService;
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
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表控制层
 */
@RestController
@RequestMapping("/web/alternativeSplicing")
@Api(value = "AlternativeSplicingController", tags = "Alternative splicing（选择性剪接）(AlternativeSplicing)")
public class AlternativeSplicingController {
    @Resource
    private AlternativeSplicingService alternativeSplicingService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = alternativeSplicingService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<AlternativeSplicing>> page(AlternativeSplicingQuery query) {
        IPage<AlternativeSplicing> result = alternativeSplicingService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, AlternativeSplicingQuery query) {
        alternativeSplicingService.exportExcel(response, query);
    }
}

