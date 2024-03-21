package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.A5ssMatsJcec;
import com.lplb.modular.model.query.A5ssMatsJcecQuery;
import com.lplb.modular.service.A5ssMatsJcecService;
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
 * @Description（描述）：A5SS.MATS.JCEC(A5ssMatsJcec)表控制层
 */
@RestController
@RequestMapping("/web/a5ssMatsJcec")
@Api(value = "A5ssMatsJcecController", tags = "A5SS.MATS.JCEC(A5ssMatsJcec)")
public class A5ssMatsJcecController {
    @Resource
    private A5ssMatsJcecService a5ssMatsJcecService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = a5ssMatsJcecService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<A5ssMatsJcec>> page(A5ssMatsJcecQuery query) {
        IPage<A5ssMatsJcec> result = a5ssMatsJcecService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, A5ssMatsJcecQuery query) {
        a5ssMatsJcecService.exportExcel(response, query);
    }
}

