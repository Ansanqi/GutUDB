package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.GeneDiseaseOmics;
import com.lplb.modular.model.query.GeneDiseaseOmicsQuery;
import com.lplb.modular.model.query.HomeSpeciesQuery;
import com.lplb.modular.model.vo.ColonDiseaseSearchHeaderVo;
import com.lplb.modular.service.GeneDiseaseOmicsService;
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
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表控制层
 */
@RestController
@RequestMapping("/web/geneDiseaseOmics")
@Api(value = "GeneDiseaseOmicsController", tags = "Gene_disease_omics(GeneDiseaseOmics)")
public class GeneDiseaseOmicsController {
    @Resource
    private GeneDiseaseOmicsService geneDiseaseOmicsService;

    @GetMapping(value = "/colonDiseaseSearchHeader")
    @ApiOperation(value = "Colon Disease表头搜索数据")
    public ResponseData<ColonDiseaseSearchHeaderVo> colonDiseaseSearchHeader() {
        ColonDiseaseSearchHeaderVo result = geneDiseaseOmicsService.colonDiseaseSearchHeader();
        return ResponseData.success(result);
    }

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = geneDiseaseOmicsService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<GeneDiseaseOmics>> page(GeneDiseaseOmicsQuery query) {
        IPage<GeneDiseaseOmics> result = geneDiseaseOmicsService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, GeneDiseaseOmicsQuery query) {
        geneDiseaseOmicsService.exportExcel(response, query);
    }
}

