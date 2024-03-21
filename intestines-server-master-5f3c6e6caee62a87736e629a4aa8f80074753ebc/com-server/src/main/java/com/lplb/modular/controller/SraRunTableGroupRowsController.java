package com.lplb.modular.controller;

import com.lplb.modular.service.SraRunTableGroupRowsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)表控制层
 */
@RestController
@RequestMapping("/web/sraRunTableGroupRows")
@Api(value = "SraRunTableGroupRowsController", tags = "SraRunTable分组（Sra运行表分组列）(SraRunTableGroupRows)")
public class SraRunTableGroupRowsController {
    @Resource
    private SraRunTableGroupRowsService sraRunTableGroupRowsService;
}

