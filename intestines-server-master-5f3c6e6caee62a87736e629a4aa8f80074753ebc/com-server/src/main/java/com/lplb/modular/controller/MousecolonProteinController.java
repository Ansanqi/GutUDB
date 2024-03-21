package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.MiRnaQuery;
import com.lplb.modular.model.query.MousecolonProteinQuery;
import com.lplb.modular.model.query.MousecolonRnaGeneDetailsQuery;
import com.lplb.modular.model.query.MousecolonRnaQuery;
import com.lplb.modular.service.MousecolonProteinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-11 09:42:06
 * @Description（描述）：GSM6578068_mousecolon_protein(MousecolonProtein)表控制层
 */
@RestController
@RequestMapping("/web/mousecolonProtein")
@Api(value = "MousecolonProteinController", tags = "GSM6578068_mousecolon_protein(MousecolonProtein)")
public class MousecolonProteinController {
    @Resource
    private MousecolonProteinService mousecolonProteinService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = mousecolonProteinService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "列表获取")
    public ResponseData<IPage<Map<String, Object>>> page(MousecolonProteinQuery query) {
        IPage<Map<String, Object>> result = mousecolonProteinService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/details")
    @ApiOperation(value = "详情")
    public ResponseData<Map<String, Object>> details(@RequestParam(value = "id") Long id) {
        Map<String, Object> result = mousecolonProteinService.details(id);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, MousecolonProteinQuery query) {
        mousecolonProteinService.exportExcel(response, query);
    }
}

