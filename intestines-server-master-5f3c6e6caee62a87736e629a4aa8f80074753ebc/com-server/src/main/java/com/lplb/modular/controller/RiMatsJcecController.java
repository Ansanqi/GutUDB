package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.RiMatsJcec;
import com.lplb.modular.model.query.RiMatsJcecQuery;
import com.lplb.modular.service.RiMatsJcecService;
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
 * @Date（日期）： 2023-07-27 18:35:43
 * @Description（描述）：RI.MATS.JCEC(RiMatsJcec)表控制层
 */
@RestController
@RequestMapping("/web/riMatsJcec")
@Api(value = "RiMatsJcecController", tags = "RI.MATS.JCEC(RiMatsJcec)")
public class RiMatsJcecController {
    @Resource
    private RiMatsJcecService riMatsJcecService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = riMatsJcecService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<RiMatsJcec>> page(RiMatsJcecQuery query) {
        IPage<RiMatsJcec> result = riMatsJcecService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, RiMatsJcecQuery query) {
        riMatsJcecService.exportExcel(response, query);
    }
}

