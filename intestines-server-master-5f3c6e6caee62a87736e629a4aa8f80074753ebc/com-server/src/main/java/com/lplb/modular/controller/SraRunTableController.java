package com.lplb.modular.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.entity.SraRunTable;
import com.lplb.modular.model.query.SraRunTableQuery;
import com.lplb.modular.service.SraRunTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表控制层
 */
@RestController
@RequestMapping("/web/sraRunTable")
@Api(value = "SraRunTableController", tags = "SraRunTable（Sra运行表）(SraRunTable)")
public class SraRunTableController {
    @Resource
    private SraRunTableService sraRunTableService;

    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "Excel导入")
    public ResponseData<Boolean> importExcel(MultipartFile file) {
        Boolean result = sraRunTableService.importExcel(file);
        return SuccessResponseData.success(result);
    }

    @GetMapping(value = "/page")
    @ApiOperation(value = "数据列表查询")
    public ResponseData<IPage<SraRunTable>> page(SraRunTableQuery query) {
        IPage<SraRunTable> result = sraRunTableService.pageList(query);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/groupInfos")
    @ApiOperation(value = "分组信息查询")
    public ResponseData<List<Map<String, Object>>> groupInfos(@RequestParam(value = "dataAccessId") String dataAccessId) {
        List<Map<String, Object>> result = sraRunTableService.groupInfos(dataAccessId);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/groupNotes")
    @ApiOperation(value = "分组Note信息查询")
    public ResponseData<String> groupNotes(@RequestParam(value = "dataAccessId") String dataAccessId) {
        String result = sraRunTableService.groupNotes(dataAccessId);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "Excel导出")
    public void exportExcel(HttpServletResponse response, SraRunTableQuery query) {
        sraRunTableService.exportExcel(response, query);
    }
}

