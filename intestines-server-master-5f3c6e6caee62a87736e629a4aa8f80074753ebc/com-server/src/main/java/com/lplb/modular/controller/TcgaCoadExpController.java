package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.service.TcgaCoadExpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-16 19:08:44
 * @Description（描述）：TCGA COAD_exp(TcgaCoadExp)表控制层
 */
@RestController
@RequestMapping("/web/tcgaCoadExp")
@Api(value = "TcgaCoadExpController", tags = "TCGA COAD_exp(TcgaCoadExp)")
public class TcgaCoadExpController {
    @Resource
    private TcgaCoadExpService tcgaCoadExpService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = tcgaCoadExpService.importExcel(file);
        return SuccessResponseData.success(result);
    }
}

