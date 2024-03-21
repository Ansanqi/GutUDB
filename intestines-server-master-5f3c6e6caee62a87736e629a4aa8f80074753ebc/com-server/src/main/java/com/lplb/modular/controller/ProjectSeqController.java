package com.lplb.modular.controller;

import com.lplb.modular.service.ProjectSeqService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 17:34:50
 * @Description（描述）：项目出现频次(ProjectSeq)表控制层
 */
@RestController
@RequestMapping("/web/projectSeq")
@Api(value = "ProjectSeqController", tags = "项目出现频次(ProjectSeq)")
public class ProjectSeqController {
    @Resource
    private ProjectSeqService projectSeqService;
}

