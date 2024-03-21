package com.lplb.modular.controller;

import com.lplb.modular.service.DiseaseGenomicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：疾病基因组(DiseaseGenomic)表控制层
 */
@RestController
@RequestMapping("/web/diseaseGenomic")
@Api(value = "DiseaseGenomicController", tags = "疾病基因组(DiseaseGenomic)")
public class DiseaseGenomicController {
    @Resource
    private DiseaseGenomicService diseaseGenomicService;
}

