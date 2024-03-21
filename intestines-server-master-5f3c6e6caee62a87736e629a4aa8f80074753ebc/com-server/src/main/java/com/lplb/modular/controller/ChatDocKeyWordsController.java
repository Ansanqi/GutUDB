package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.ChatDocKeyWords;
import com.lplb.modular.service.ChatDocKeyWordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc_key_words(ChatDocKeyWords)表控制层
 */
@RestController
@RequestMapping("/web/chatDocKeyWords")
@Api(value = "ChatDocKeyWordsController", tags = "Chatdoc_key_words(ChatDocKeyWords)")
public class ChatDocKeyWordsController {
    @Resource
    private ChatDocKeyWordsService chatDocKeyWordsService;

    @GetMapping(value = "/addKeyWords")
    @ApiOperation(value = "热词添加")
    public ResponseData<Boolean> addKeyWords(@RequestParam(value = "keyWords") String keyWords) {
        Boolean result = chatDocKeyWordsService.addKeyWords(keyWords);
        return ResponseData.success(result);
    }

    @GetMapping(value = "/top10")
    @ApiOperation(value = "top10")
    public ResponseData<List<ChatDocKeyWords>> top10() {
        List<ChatDocKeyWords> result = chatDocKeyWordsService.top10();
        return ResponseData.success(result);
    }


}

