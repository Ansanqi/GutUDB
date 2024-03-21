package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.CnaGenesQuery;
import com.lplb.modular.model.vo.CnaGenesVo;
import com.lplb.modular.service.CnaGenesService;
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
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：CNA Genes（CAN基因）(CnaGenes)表控制层
 */
@RestController
@RequestMapping("/web/cnaGenes")
@Api(value = "CnaGenesController", tags = "CNA Genes（CAN基因）(CnaGenes)")
public class CnaGenesController {
    @Resource
    private CnaGenesService cnaGenesService;


    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = cnaGenesService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<CnaGenesVo>> page(CnaGenesQuery query) {
        IPage<CnaGenesVo> result = cnaGenesService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, CnaGenesQuery query) {
        cnaGenesService.exportExcel(response, query);
    }
}

