package com.lplb.modular.controller;

import com.lplb.modular.service.MousecolonRnaGeneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-07 15:05:43
 * @Description（描述）：mousecolon_RNA-GENE列表(MousecolonRnaGene)表控制层
 */
@RestController
@RequestMapping("/web/mousecolonRnaGene")
@Api(value = "MousecolonRnaGeneController", tags = "mousecolon_RNA-GENE列表(MousecolonRnaGene)")
public class MousecolonRnaGeneController {
    @Resource
    private MousecolonRnaGeneService mousecolonRnaGeneService;
}

