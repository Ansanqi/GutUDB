package com.lplb.modular.controller;

import com.lplb.modular.service.OmicsSourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：来源(OmicsSource)表控制层
 */
@RestController
@RequestMapping("/web/omicsSource")
@Api(value = "OmicsSourceController", tags = "来源(OmicsSource)")
public class OmicsSourceController {
    @Resource
    private OmicsSourceService omicsSourceService;
}

