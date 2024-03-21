package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.VirulenceGene;
import com.lplb.modular.model.query.VirulenceGeneQuery;
import com.lplb.modular.service.VirulenceGeneService;
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
 * @Date（日期）： 2023-07-27 18:35:47
 * @Description（描述）：Virulence gene（毒性基因）(VirulenceGene)表控制层
 */
@RestController
@RequestMapping("/web/virulenceGene")
@Api(value = "VirulenceGeneController", tags = "Virulence gene（毒性基因）(VirulenceGene)")
public class VirulenceGeneController {
    @Resource
    private VirulenceGeneService virulenceGeneService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = virulenceGeneService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<VirulenceGene>> page(VirulenceGeneQuery query) {
        IPage<VirulenceGene> result = virulenceGeneService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, VirulenceGeneQuery query) {
        virulenceGeneService.exportExcel(response, query);
    }
}

