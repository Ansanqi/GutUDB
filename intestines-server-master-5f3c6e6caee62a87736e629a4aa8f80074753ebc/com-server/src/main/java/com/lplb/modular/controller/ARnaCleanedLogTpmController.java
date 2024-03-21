package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.ARnaCleanedLogTpmQuery;
import com.lplb.modular.model.vo.ARnaCleanedLogTpmVo;
import com.lplb.modular.service.ARnaCleanedLogTpmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表控制层
 */
@RestController
@RequestMapping("/web/aRnaCleanedLogTpm")
@Api(value = "ARnaCleanedLogTpmController", tags = "A RNA cleaned log TPM(ARnaCleanedLogTpm)")
public class ARnaCleanedLogTpmController {
    @Resource
    private ARnaCleanedLogTpmService aRnaCleanedLogTpmService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = aRnaCleanedLogTpmService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<ARnaCleanedLogTpmVo>> page(ARnaCleanedLogTpmQuery query) {
        IPage<ARnaCleanedLogTpmVo> result = aRnaCleanedLogTpmService.pageList(query);
        return ResponseData.success(result);
    }
}

