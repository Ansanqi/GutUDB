package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.ChemicalCompounds;
import com.lplb.modular.model.query.ChemicalCompoundsQuery;
import com.lplb.modular.model.query.GeneDiseaseOmicsQuery;
import com.lplb.modular.service.ChemicalCompoundsService;
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
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表控制层
 */
@RestController
@RequestMapping("/web/chemicalCompounds")
@Api(value = "ChemicalCompoundsController", tags = "Chemical compounds（化合物）(ChemicalCompounds)")
public class ChemicalCompoundsController {
    @Resource
    private ChemicalCompoundsService chemicalCompoundsService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = chemicalCompoundsService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<ChemicalCompounds>> page(ChemicalCompoundsQuery query) {
        IPage<ChemicalCompounds> result = chemicalCompoundsService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, ChemicalCompoundsQuery query) {
        chemicalCompoundsService.exportExcel(response, query);
    }
}

