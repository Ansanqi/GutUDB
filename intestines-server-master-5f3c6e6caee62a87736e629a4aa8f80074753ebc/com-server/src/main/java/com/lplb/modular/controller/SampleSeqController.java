package com.lplb.modular.controller;

import com.lplb.modular.service.SampleSeqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:05:24
 * @Description（描述）：样本出现频次(SampleSeq)表控制层
 */
@RestController
@RequestMapping("/web/sampleSeq")
@Api(value = "SampleSeqController", tags = "样本出现频次(SampleSeq)")
public class SampleSeqController {
    @Resource
    private SampleSeqService sampleSeqService;
}

