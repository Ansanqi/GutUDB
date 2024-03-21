package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.service.PdfToImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-09 22:39
 * @Description（描述）：PdfToImgController
 */
@RestController
@RequestMapping("/web/pdfToImg")
@Api(value = "PdfToImgController", tags = "PDF转图片")
public class PdfToImgController {

    @Resource
    private PdfToImgService pdfToImgService;

    @GetMapping(value = "/pdfToImg")
    @ApiOperation(value = "PDF转图片")
    public ResponseData<Boolean> pdfToImg(@RequestParam(value = "filePath") String filePath) {
        Boolean result = pdfToImgService.pdfToImg(filePath);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/fileRename")
    @ApiOperation(value = "文件重命名")
    public ResponseData<Boolean> fileRename(@RequestParam(value = "filePath") String filePath) {
        Boolean result = pdfToImgService.fileRename(filePath);
        return ResponseData.success(result);
    }
}
