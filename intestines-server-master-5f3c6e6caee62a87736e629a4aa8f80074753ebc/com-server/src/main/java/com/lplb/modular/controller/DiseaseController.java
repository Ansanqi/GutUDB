package com.lplb.modular.controller;

import com.lplb.modular.service.DiseaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:42:38
 * @Description（描述）：疾病(Disease)表控制层
 */
@RestController
@RequestMapping("/web/disease")
@Api(value = "DiseaseController", tags = "疾病(Disease)")
public class DiseaseController {
    @Resource
    private DiseaseService diseaseService;
}

