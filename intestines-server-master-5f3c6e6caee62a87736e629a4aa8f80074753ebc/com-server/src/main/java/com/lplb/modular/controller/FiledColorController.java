package com.lplb.modular.controller;

import com.lplb.modular.service.FiledColorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-17 20:16:03
 * @Description（描述）：(FiledColor)表控制层
 */
@RestController
@RequestMapping("/web/filedColor")
@Api(value = "FiledColorController", tags = "(FiledColor)")
public class FiledColorController {
    @Resource
    private FiledColorService filedColorService;
}

