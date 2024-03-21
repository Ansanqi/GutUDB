package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.ChineseMedicine;
import com.lplb.modular.model.query.ChineseMedicineQuery;
import com.lplb.modular.service.ChineseMedicineService;
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
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表控制层
 */
@RestController
@RequestMapping("/web/chineseMedicine")
@Api(value = "ChineseMedicineController", tags = "Chinese Medicine（中药）(ChineseMedicine)")
public class ChineseMedicineController {
    @Resource
    private ChineseMedicineService chineseMedicineService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = chineseMedicineService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<ChineseMedicine>> page(ChineseMedicineQuery query) {
        IPage<ChineseMedicine> result = chineseMedicineService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, ChineseMedicineQuery query) {
        chineseMedicineService.exportExcel(response, query);
    }
}

