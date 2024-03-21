package com.lplb.modular.controller;

import com.lplb.modular.service.GeneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:41:02
 * @Description（描述）：基因(Gene)表控制层
 */
@RestController
@RequestMapping("/web/gene")
@Api(value = "GeneController", tags = "基因(Gene)")
public class GeneController {
    @Resource
    private GeneService geneService;
}

