package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.vo.GeneDetailVo;
import com.lplb.modular.model.vo.ProjectDetailVo;
import com.lplb.modular.service.ProjectDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-03 22:30
 * @Description（描述）：ProjectDetailController
 */
@RestController
@RequestMapping("/web/projectDetail")
@Api(value = "ProjectDetailController", tags = "项目详情")
public class ProjectDetailController {

    @Resource
    private ProjectDetailService projectDetailService;

    @PostMapping(value = "/projectDetailByProjectNo")
    @ApiOperation(value = "根据项目编号查询项目详情")
    public ResponseData<ProjectDetailVo> projectDetailByProjectNo(@RequestParam(value = "projectNo") String projectNo) {
        ProjectDetailVo result = projectDetailService.projectDetailByProjectNo(projectNo);
        return SuccessResponseData.success(result);
    }

}
