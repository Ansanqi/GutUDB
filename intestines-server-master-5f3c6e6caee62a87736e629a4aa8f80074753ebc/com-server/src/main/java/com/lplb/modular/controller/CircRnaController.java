package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.CircRna;
import com.lplb.modular.model.query.CircRnaQuery;
import com.lplb.modular.model.query.GeoInformationQuery;
import com.lplb.modular.service.CircRnaService;
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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：circRNA（环核糖核酸）(CircRna)表控制层
 */
@RestController
@RequestMapping("/web/circRna")
@Api(value = "CircRnaController", tags = "circRNA（环核糖核酸）(CircRna)")
public class CircRnaController {
    @Resource
    private CircRnaService circRnaService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = circRnaService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<CircRna>> page(CircRnaQuery query) {
        IPage<CircRna> result = circRnaService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, CircRnaQuery query) {
        circRnaService.exportExcel(response, query);
    }
}

