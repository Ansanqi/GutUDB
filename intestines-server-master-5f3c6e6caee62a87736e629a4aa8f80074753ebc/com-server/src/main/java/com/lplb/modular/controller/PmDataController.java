package com.lplb.modular.controller;

import com.lplb.modular.service.PmDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：文献数据库(PmData)表控制层
 */
@RestController
@RequestMapping("/web/pmData")
@Api(value = "PmDataController", tags = "文献数据库(PmData)")
public class PmDataController {
    @Resource
    private PmDataService pmDataService;
}

