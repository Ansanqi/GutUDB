package com.lplb.modular.controller;

import com.lplb.modular.service.TissueCellLineService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:27:11
 * @Description（描述）：Tissue/Cell Line（组织/细胞系）(TissueCellLine)表控制层
 */
@RestController
@RequestMapping("/web/tissueCellLine")
@Api(value = "TissueCellLineController", tags = "Tissue/Cell Line（组织/细胞系）(TissueCellLine)")
public class TissueCellLineController {
    @Resource
    private TissueCellLineService tissueCellLineService;
}

