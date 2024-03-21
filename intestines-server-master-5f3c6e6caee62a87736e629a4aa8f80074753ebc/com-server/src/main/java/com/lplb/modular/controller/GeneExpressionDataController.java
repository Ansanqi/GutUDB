package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.GeneExpressionDataQuery;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import com.lplb.modular.service.GeneExpressionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表控制层
 */
@RestController
@RequestMapping("/web/geneExpressionData")
@Api(value = "GeneExpressionDataController", tags = "Gene expression data（基因表达数据）(GeneExpressionData)")
public class GeneExpressionDataController {
    @Resource
    private GeneExpressionDataService geneExpressionDataService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = geneExpressionDataService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<GeneExpressionDataVo>> page(GeneExpressionDataQuery query) {
        IPage<GeneExpressionDataVo> result = geneExpressionDataService.pageList(query);
        return ResponseData.success(result);
    }
}

