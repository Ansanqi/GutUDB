package com.lplb.modular.controller;

import com.lplb.modular.service.DiseaseSeqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:42:39
 * @Description（描述）：疾病出现频次(DiseaseSeq)表控制层
 */
@RestController
@RequestMapping("/web/diseaseSeq")
@Api(value = "DiseaseSeqController", tags = "疾病出现频次(DiseaseSeq)")
public class DiseaseSeqController {
    @Resource
    private DiseaseSeqService diseaseSeqService;
}

