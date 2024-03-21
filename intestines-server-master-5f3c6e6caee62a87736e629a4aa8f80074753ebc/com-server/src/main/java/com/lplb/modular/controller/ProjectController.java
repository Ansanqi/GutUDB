package com.lplb.modular.controller;

import com.lplb.modular.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 17:34:46
 * @Description（描述）：Project（项目）(Project)表控制层
 */
@RestController
@RequestMapping("/web/project")
@Api(value = "ProjectController", tags = "Project（项目）(Project)")
public class ProjectController {
    @Resource
    private ProjectService projectService;
}

