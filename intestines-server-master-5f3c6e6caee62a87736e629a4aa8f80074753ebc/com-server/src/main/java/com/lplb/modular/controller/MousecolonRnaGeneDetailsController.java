package com.lplb.modular.controller;

import com.lplb.modular.service.MousecolonRnaGeneDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-07 15:05:43
 * @Description（描述）：mousecolon_RNA-GENE详情(MousecolonRnaGeneDetails)表控制层
 */
@RestController
@RequestMapping("/web/mousecolonRnaGeneDetails")
@Api(value = "MousecolonRnaGeneDetailsController", tags = "mousecolon_RNA-GENE详情(MousecolonRnaGeneDetails)")
public class MousecolonRnaGeneDetailsController {
    @Resource
    private MousecolonRnaGeneDetailsService mousecolonRnaGeneDetailsService;
}

