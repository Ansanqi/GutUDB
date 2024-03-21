package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.MiRna;
import com.lplb.modular.model.query.LncRnaQuery;
import com.lplb.modular.model.query.MiRnaQuery;
import com.lplb.modular.service.MiRnaService;
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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：miRNA（微型核糖核酸）(MiRna)表控制层
 */
@RestController
@RequestMapping("/web/miRna")
@Api(value = "MiRnaController", tags = "miRNA（微型核糖核酸）(MiRna)")
public class MiRnaController {
    @Resource
    private MiRnaService miRnaService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = miRnaService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<MiRna>> page(MiRnaQuery query) {
        IPage<MiRna> result = miRnaService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, MiRnaQuery query) {
        miRnaService.exportExcel(response, query);
    }
}

