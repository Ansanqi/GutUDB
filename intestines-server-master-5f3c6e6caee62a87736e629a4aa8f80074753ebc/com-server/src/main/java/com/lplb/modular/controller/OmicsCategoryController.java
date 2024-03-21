package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.OmicsCategory;
import com.lplb.modular.model.vo.OmicsCategoryTreeVo;
import com.lplb.modular.service.OmicsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-28 18:15:46
 * @Description（描述）：分类表(OmicsCategory)表控制层
 */
@RestController
@RequestMapping("/web/omicsCategory")
@Api(value = "OmicsCategoryController", tags = "分类表(OmicsCategory)")
public class OmicsCategoryController {
    @Resource
    private OmicsCategoryService omicsCategoryService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增分类")
    public ResponseData<Boolean> insert(@RequestBody @ModelAttribute OmicsCategory omicsCategory) {
        Boolean result = omicsCategoryService.insert(omicsCategory);
        return ResponseData.success(result);
    }

    @PostMapping(value = "/categoryTree")
    @ApiOperation(value = "分类树")
    public ResponseData<List<OmicsCategoryTreeVo>> categoryTree(@RequestParam(value = "parentId") Long parentId) {
        List<OmicsCategoryTreeVo> result = omicsCategoryService.categoryTree(parentId);
        return ResponseData.success(result);
    }
}

