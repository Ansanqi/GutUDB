package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.A3ssMatsJece;
import com.lplb.modular.model.query.A3ssMatsJeceQuery;
import com.lplb.modular.service.A3ssMatsJeceService;
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
 * @Description（描述）：A3SS.MATS.JCEC(A3ssMatsJece)表控制层
 */
@RestController
@RequestMapping("/web/a3ssMatsJece")
@Api(value = "A3ssMatsJeceController", tags = "A3SS.MATS.JCEC(A3ssMatsJece)")
public class A3ssMatsJeceController {
    @Resource
    private A3ssMatsJeceService a3ssMatsJeceService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = a3ssMatsJeceService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<A3ssMatsJece>> page(A3ssMatsJeceQuery query) {
        IPage<A3ssMatsJece> result = a3ssMatsJeceService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, A3ssMatsJeceQuery query) {
        a3ssMatsJeceService.exportExcel(response, query);
    }
}

