package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.service.RbpAndTfListService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-01 16:16:05
 * @Description（描述）：RBP and TF list表(RbpAndTfList)表控制层
 */
@RestController
@RequestMapping("/web/rbpAndTfList")
@Api(value = "RbpAndTfListController", tags = "RBP and TF list表(RbpAndTfList)")
public class RbpAndTfListController {
    @Resource
    private RbpAndTfListService rbpAndTfListService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = rbpAndTfListService.importExcel(file);
        return SuccessResponseData.success(result);
    }
}

