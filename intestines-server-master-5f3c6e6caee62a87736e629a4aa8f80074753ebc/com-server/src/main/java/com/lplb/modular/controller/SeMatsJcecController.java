package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.SeMatsJcec;
import com.lplb.modular.model.query.SeMatsJcecQuery;
import com.lplb.modular.service.SeMatsJcecService;
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
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SE.MATS.JCEC(SeMatsJcec)表控制层
 */
@RestController
@RequestMapping("/web/seMatsJcec")
@Api(value = "SeMatsJcecController", tags = "SE.MATS.JCEC(SeMatsJcec)")
public class SeMatsJcecController {
    @Resource
    private SeMatsJcecService seMatsJcecService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = seMatsJcecService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<SeMatsJcec>> page(SeMatsJcecQuery query) {
        IPage<SeMatsJcec> result = seMatsJcecService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, SeMatsJcecQuery query) {
        seMatsJcecService.exportExcel(response, query);
    }
}

