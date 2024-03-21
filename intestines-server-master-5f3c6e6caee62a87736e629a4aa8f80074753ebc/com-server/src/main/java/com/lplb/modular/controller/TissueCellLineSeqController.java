package com.lplb.modular.controller;

import com.lplb.modular.service.TissueCellLineSeqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:27:16
 * @Description（描述）：组织/细胞系出现频次(TissueCellLineSeq)表控制层
 */
@RestController
@RequestMapping("/web/tissueCellLineSeq")
@Api(value = "TissueCellLineSeqController", tags = "组织/细胞系出现频次(TissueCellLineSeq)")
public class TissueCellLineSeqController {
    @Resource
    private TissueCellLineSeqService tissueCellLineSeqService;
}

