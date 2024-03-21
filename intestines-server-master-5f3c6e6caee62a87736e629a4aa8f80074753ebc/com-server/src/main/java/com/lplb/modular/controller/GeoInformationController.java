package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.GeoInformation;
import com.lplb.modular.model.query.GeoInformationQuery;
import com.lplb.modular.model.query.SeMatsJcecQuery;
import com.lplb.modular.service.GeoInformationService;
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
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表控制层
 */
@RestController
@RequestMapping("/web/geoInformation")
@Api(value = "GeoInformationController", tags = "GEO_information(GeoInformation)")
public class GeoInformationController {
    @Resource
    private GeoInformationService geoInformationService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = geoInformationService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<GeoInformation>> page(GeoInformationQuery query) {
        IPage<GeoInformation> result = geoInformationService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, GeoInformationQuery query) {
        geoInformationService.exportExcel(response, query);
    }
}

