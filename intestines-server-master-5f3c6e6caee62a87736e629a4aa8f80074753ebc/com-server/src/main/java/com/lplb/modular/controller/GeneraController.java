package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.Genera;
import com.lplb.modular.model.query.GeneraQuery;
import com.lplb.modular.model.query.ProteomicsQuery;
import com.lplb.modular.service.GeneraService;
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
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表控制层
 */
@RestController
@RequestMapping("/web/genera")
@Api(value = "GeneraController", tags = "Genera（属群）(Genera)")
public class GeneraController {
    @Resource
    private GeneraService generaService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = generaService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<Genera>> page(GeneraQuery query) {
        IPage<Genera> result = generaService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, GeneraQuery query) {
        generaService.exportExcel(response, query);
    }
}

