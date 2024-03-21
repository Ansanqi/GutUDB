package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.ColonDisease;
import com.lplb.modular.service.ColonDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Colon Disease（肠道疾病）(ColonDisease)表控制层
 */
@RestController
@RequestMapping("/web/colonDisease")
@Api(value = "ColonDiseaseController", tags = "Colon Disease（肠道疾病）(ColonDisease)")
public class ColonDiseaseController {
    @Resource
    private ColonDiseaseService colonDiseaseService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增")
    public ResponseData<Boolean> insert(@RequestBody @ModelAttribute ColonDisease colonDisease) {
        Boolean result = colonDiseaseService.insert(colonDisease);
        return ResponseData.success(result);
    }
}

