package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.MousecolonRnaGeneDetailsQuery;
import com.lplb.modular.model.query.MousecolonRnaQuery;
import com.lplb.modular.service.MousecolonRnaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 11:00
 * @Description（描述）：MousecolonRnaController
 */
@RestController
@RequestMapping("/web/mousecolonRna")
@Api(value = "MousecolonRnaController", tags = "Proteomics(GSM6578059_mousecolon_RNA)")
public class MousecolonRnaController {

    @Resource
    private MousecolonRnaService mousecolonRnaService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = mousecolonRnaService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "列表获取")
    public ResponseData<IPage<Map<String, Object>>> page(MousecolonRnaQuery query) {
        IPage<Map<String, Object>> result = mousecolonRnaService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/details")
    @ApiOperation(value = "详情")
    public ResponseData<Map<String, Object>> details(MousecolonRnaGeneDetailsQuery query) {
        Map<String, Object> result = mousecolonRnaService.details(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public ResponseData<String> exportExcel(HttpServletResponse response) {
        String result = mousecolonRnaService.exportExcel(response);
        return ResponseData.success(result);
    }
}
