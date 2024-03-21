package com.lplb.modular.controller;

import com.lplb.modular.service.SraRunTableGroupNotesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-29 09:24:10
 * @Description（描述）：SraRunTable分组Note(SraRunTableGroupNotes)表控制层
 */
@RestController
@RequestMapping("/web/sraRunTableGroupNotes")
@Api(value = "SraRunTableGroupNotesController", tags = "SraRunTable分组Note(SraRunTableGroupNotes)")
public class SraRunTableGroupNotesController {
    @Resource
    private SraRunTableGroupNotesService sraRunTableGroupNotesService;
}

