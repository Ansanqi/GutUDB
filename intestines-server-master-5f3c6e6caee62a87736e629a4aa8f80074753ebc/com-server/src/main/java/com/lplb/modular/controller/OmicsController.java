package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.Omics;
import com.lplb.modular.service.OmicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：组学(Omics)表控制层
 */
@RestController
@RequestMapping("/web/omics")
@Api(value = "OmicsController", tags = "组学(Omics)")
public class OmicsController {
    @Resource
    private OmicsService omicsService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增组学")
    public ResponseData<Boolean> insert(@RequestBody @ModelAttribute Omics omics) {
        Boolean result = omicsService.insert(omics);
        return ResponseData.success(result);
    }
}

