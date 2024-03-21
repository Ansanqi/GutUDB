package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.query.SingleCellGeneExpressDataQuery;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataUniqueVo;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataVo;
import com.lplb.modular.service.SingleCellGeneExpressDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表控制层
 */
@RestController
@RequestMapping("/web/singleCellGeneExpressData")
@Api(value = "SingleCellGeneExpressDataController", tags = "单细胞组学基因表达数据详情(SingleCellGeneExpressData)")
public class SingleCellGeneExpressDataController {
    @Resource
    private SingleCellGeneExpressDataService singleCellGeneExpressDataService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = singleCellGeneExpressDataService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/uniqueIdList")
    @ApiOperation(value = "查询唯一键数据列表数据")
    public ResponseData<List<SingleCellGeneExpressDataUniqueVo>> uniqueIdList() {
        List<SingleCellGeneExpressDataUniqueVo> result = singleCellGeneExpressDataService.uniqueIdList();
        return ResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<SingleCellGeneExpressDataVo>> page(SingleCellGeneExpressDataQuery query) {
        IPage<SingleCellGeneExpressDataVo> result = singleCellGeneExpressDataService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, SingleCellGeneExpressDataQuery query) {
        singleCellGeneExpressDataService.exportExcel(response, query);
    }
}

