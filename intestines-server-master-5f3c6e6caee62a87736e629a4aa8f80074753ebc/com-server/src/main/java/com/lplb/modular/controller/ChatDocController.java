package com.lplb.modular.controller;

import com.lplb.core.pojo.response.ResponseData;
import com.lplb.modular.model.entity.ChatDoc;
import com.lplb.modular.model.vo.ChatDocVo;
import com.lplb.modular.service.ChatDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc(ChatDoc)表控制层
 */
@RestController
@RequestMapping("/web/chatDoc")
@Api(value = "ChatDocController", tags = "Chatdoc(ChatDoc)")
public class ChatDocController {
    @Resource
    private ChatDocService chatDocService;

    @PostMapping(value = "/addChatDoc")
    @ApiOperation(value = "addChatDoc添加")
    public ResponseData<Boolean> addKeyWords(@RequestBody ChatDoc chatDoc) {
        Boolean result = chatDocService.save(chatDoc);
        return ResponseData.success(result);
    }

    @PostMapping(value = "/chat")
    @ApiOperation(value = "chat")
    public ResponseData<List<ChatDocVo>> chat(@RequestParam(value = "searchText") String searchText,
                                              @RequestParam(value = "isKeyWords", required = false) Integer isKeyWords) {
        List<ChatDocVo> result = chatDocService.chat(searchText, isKeyWords);

        return ResponseData.success(result);
    }
}

