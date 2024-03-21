package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.MxeMatsJcec;
import com.lplb.modular.model.query.MxeMatsJcecQuery;
import com.lplb.modular.service.MxeMatsJcecService;
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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：MXE.MATS.JCEC(MxeMatsJcec)表控制层
 */
@RestController
@RequestMapping("/web/mxeMatsJcec")
@Api(value = "MxeMatsJcecController", tags = "MXE.MATS.JCEC(MxeMatsJcec)")
public class MxeMatsJcecController {
    @Resource
    private MxeMatsJcecService mxeMatsJcecService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = mxeMatsJcecService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<MxeMatsJcec>> page(MxeMatsJcecQuery query) {
        IPage<MxeMatsJcec> result = mxeMatsJcecService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, MxeMatsJcecQuery query) {
        mxeMatsJcecService.exportExcel(response, query);
    }
}

