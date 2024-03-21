package com.lplb.modular.controller;

import com.lplb.modular.service.GeneSeqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:41:03
 * @Description（描述）：基因出现频次(GeneSeq)表控制层
 */
@RestController
@RequestMapping("/web/geneSeq")
@Api(value = "GeneSeqController", tags = "基因出现频次(GeneSeq)")
public class GeneSeqController {
    @Resource
    private GeneSeqService geneSeqService;
}

