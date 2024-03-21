package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.OmicsLevel;
import com.lplb.modular.service.OmicsLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：等级(OmicsLevel)表控制层
 */
@RestController
@RequestMapping("/web/omicsLevel")
@Api(value = "OmicsLevelController", tags = "等级(OmicsLevel)")
public class OmicsLevelController {
    @Resource
    private OmicsLevelService omicsLevelService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增")
    public ResponseData<Boolean> insert(@RequestBody @ModelAttribute OmicsLevel omicsLevel) {
        Boolean result = omicsLevelService.insert(omicsLevel);
        return ResponseData.success(result);
    }
}

