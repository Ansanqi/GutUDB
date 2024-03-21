package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.ChromosomeStructure;
import com.lplb.modular.model.query.ChromosomeStructureQuery;
import com.lplb.modular.service.ChromosomeStructureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chromosome structure（染色体结构）(ChromosomeStructure)表控制层
 */
@RestController
@RequestMapping("/web/chromosomeStructure")
@Api(value = "ChromosomeStructureController", tags = "Chromosome structure（染色体结构）(ChromosomeStructure)")
public class ChromosomeStructureController {
    @Resource
    private ChromosomeStructureService chromosomeStructureService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = chromosomeStructureService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<ChromosomeStructure>> page(ChromosomeStructureQuery query) {
        IPage<ChromosomeStructure> result = chromosomeStructureService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, ChromosomeStructureQuery query) {
        chromosomeStructureService.exportExcel(response, query);
    }
}

