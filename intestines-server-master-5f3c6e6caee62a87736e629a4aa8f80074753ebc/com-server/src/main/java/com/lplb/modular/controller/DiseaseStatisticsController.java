package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.service.DiseaseStatisticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-07 10:39:03
 * @Description（描述）：疾病统计表(DiseaseStatistics)表控制层
 */
@RestController
@RequestMapping("/web/diseaseStatistics")
@Api(value = "DiseaseStatisticsController", tags = "疾病统计表(DiseaseStatistics)")
public class DiseaseStatisticsController {
    @Resource
    private DiseaseStatisticsService diseaseStatisticsService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = diseaseStatisticsService.importExcel(file);
        return SuccessResponseData.success(result);
    }
}

