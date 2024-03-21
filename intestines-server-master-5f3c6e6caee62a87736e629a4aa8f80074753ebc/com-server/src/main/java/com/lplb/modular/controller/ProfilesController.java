package com.lplb.modular.controller;

import com.lplb.modular.service.ProfilesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Profiles（图样）(Profiles)表控制层
 */
@RestController
@RequestMapping("/web/profiles")
@Api(value = "ProfilesController", tags = "Profiles（图样）(Profiles)")
public class ProfilesController {
    @Resource
    private ProfilesService profilesService;
}

