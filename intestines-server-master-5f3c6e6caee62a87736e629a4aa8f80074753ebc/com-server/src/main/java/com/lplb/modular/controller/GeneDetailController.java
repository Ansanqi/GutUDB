package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.modular.model.vo.GeneDetailVo;
import com.lplb.modular.service.GeneDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-01 16:41
 * @Description（描述）：GeneDetailController
 */
@RestController
@RequestMapping("/web/geneDetail")
@Api(value = "GeneDetailController", tags = "基因详情")
public class GeneDetailController {

    @Resource
    private GeneDetailService geneDetailService;

    @GetMapping(value = "/diseaseList")
    @ApiOperation(value = "查询筛选疾病列表")
    public ResponseData<List<String>> diseaseList() {
        List<String> result = geneDetailService.diseaseList();
        return ResponseData.success(result);
    }

    @PostMapping(value = "/geneDetailByGeneName")
    @ApiOperation(value = "根据基因名称查询基因详情")
    public ResponseData<GeneDetailVo> geneDetailByGeneName(@RequestParam(value = "geneName") String geneName,
                                                           @RequestParam(value = "disease", required = false) String disease) {
        GeneDetailVo result = geneDetailService.geneDetailByGeneName(geneName, disease);
        return SuccessResponseData.success(result);
    }

}
