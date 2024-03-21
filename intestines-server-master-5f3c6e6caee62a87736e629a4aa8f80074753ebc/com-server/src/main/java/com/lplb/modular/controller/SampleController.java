package com.lplb.modular.controller;

import com.lplb.modular.service.SampleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:05:27
 * @Description（描述）：Sample（样本）(Sample)表控制层
 */
@RestController
@RequestMapping("/web/sample")
@Api(value = "SampleController", tags = "Sample（样本）(Sample)")
public class SampleController {
    @Resource
    private SampleService sampleService;
}

