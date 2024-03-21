package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.HomoSapiensNgsm6aQuery;
import com.lplb.modular.model.query.SpatialHistologyQuery;
import com.lplb.modular.model.vo.SpatialHistologyVo;
import com.lplb.modular.service.SpatialHistologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：Spatial histology(SpatialHistology)表控制层
 */
@RestController
@RequestMapping("/web/spatialHistology")
@Api(value = "SpatialHistologyController", tags = "Spatial histology(SpatialHistology)")
public class SpatialHistologyController {
    @Resource
    private SpatialHistologyService spatialHistologyService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = spatialHistologyService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<SpatialHistologyVo>> page(SpatialHistologyQuery query) {
        IPage<SpatialHistologyVo> result = spatialHistologyService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/details")
    @ApiOperation(value = "详情查询")
    public ResponseData<SpatialHistologyVo> details(@RequestParam(value = "id") Long id) {
        SpatialHistologyVo result = spatialHistologyService.details(id);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, SpatialHistologyQuery query) {
        spatialHistologyService.exportExcel(response, query);
    }
}

