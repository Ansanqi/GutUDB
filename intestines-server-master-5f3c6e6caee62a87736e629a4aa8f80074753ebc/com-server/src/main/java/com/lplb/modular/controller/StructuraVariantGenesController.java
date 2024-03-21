package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.StructuraVariantGenesQuery;
import com.lplb.modular.model.vo.StructuraVariantGenesVo;
import com.lplb.modular.service.StructuraVariantGenesService;
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
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Structural Variant Genes（结构变异基因）(StructuraVariantGenes)表控制层
 */
@RestController
@RequestMapping("/web/structuraVariantGenes")
@Api(value = "StructuraVariantGenesController", tags = "Structural Variant Genes（结构变异基因）(StructuraVariantGenes)")
public class StructuraVariantGenesController {
    @Resource
    private StructuraVariantGenesService structuraVariantGenesService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = structuraVariantGenesService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<StructuraVariantGenesVo>> page(StructuraVariantGenesQuery query) {
        IPage<StructuraVariantGenesVo> result = structuraVariantGenesService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, StructuraVariantGenesQuery query) {
        structuraVariantGenesService.exportExcel(response, query);
    }
}

