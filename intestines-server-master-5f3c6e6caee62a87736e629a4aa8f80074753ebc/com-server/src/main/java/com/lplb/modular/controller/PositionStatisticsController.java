package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.service.PositionStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-08 11:42:05
 * @Description（描述）：Position统计表(PositionStatistics)表控制层
 */
@RestController
@RequestMapping("/web/positionStatistics")
@Api(value = "PositionStatisticsController", tags = "Position统计表(PositionStatistics)")
public class PositionStatisticsController {
    @Resource
    private PositionStatisticsService positionStatisticsService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = positionStatisticsService.importExcel(file);
        return SuccessResponseData.success(result);
    }
}

