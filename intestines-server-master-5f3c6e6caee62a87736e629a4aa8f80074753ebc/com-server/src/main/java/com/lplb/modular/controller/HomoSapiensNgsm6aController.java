package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.HomoSapiensNgsm6a;
import com.lplb.modular.model.query.HomoSapiensNgsm6aQuery;
import com.lplb.modular.model.query.MiRnaQuery;
import com.lplb.modular.service.HomoSapiensNgsm6aService;
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
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表控制层
 */
@RestController
@RequestMapping("/web/homoSapiensNgsm6a")
@Api(value = "HomoSapiensNgsm6aController", tags = "HomoSapiens_NGSm6A(HomoSapiensNgsm6a)")
public class HomoSapiensNgsm6aController {
    @Resource
    private HomoSapiensNgsm6aService homoSapiensNgsm6aService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = homoSapiensNgsm6aService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<HomoSapiensNgsm6a>> page(HomoSapiensNgsm6aQuery query) {
        IPage<HomoSapiensNgsm6a> result = homoSapiensNgsm6aService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, HomoSapiensNgsm6aQuery query) {
        homoSapiensNgsm6aService.exportExcel(response, query);
    }
}

