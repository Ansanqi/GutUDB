package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.Probiotics;
import com.lplb.modular.model.query.ChineseMedicineQuery;
import com.lplb.modular.model.query.ProbioticsQuery;
import com.lplb.modular.service.ProbioticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表控制层
 */
@RestController
@RequestMapping("/web/probiotics")
@Api(value = "ProbioticsController", tags = "Probiotics（益生菌）(Probiotics)")
public class ProbioticsController {
    @Resource
    private ProbioticsService probioticsService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = probioticsService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<Probiotics>> page(ProbioticsQuery query) {
        IPage<Probiotics> result = probioticsService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, ProbioticsQuery query) {
        probioticsService.exportExcel(response, query);
    }
}

