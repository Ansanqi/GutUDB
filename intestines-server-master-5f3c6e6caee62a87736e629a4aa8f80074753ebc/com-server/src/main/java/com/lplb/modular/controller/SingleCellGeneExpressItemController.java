package com.lplb.modular.controller;

import com.lplb.modular.service.SingleCellGeneExpressItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据项(SingleCellGeneExpressItem)表控制层
 */
@RestController
@RequestMapping("/web/singleCellGeneExpressItem")
@Api(value = "SingleCellGeneExpressItemController", tags = "单细胞组学基因表达数据项(SingleCellGeneExpressItem)")
public class SingleCellGeneExpressItemController {
    @Resource
    private SingleCellGeneExpressItemService singleCellGeneExpressItemService;
}

