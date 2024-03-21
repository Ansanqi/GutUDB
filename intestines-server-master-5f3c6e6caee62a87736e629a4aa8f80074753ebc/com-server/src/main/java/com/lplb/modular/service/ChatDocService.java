package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ChatDoc;
import com.lplb.modular.model.vo.ChatDocVo;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc(ChatDoc)表服务接口
 */
public interface ChatDocService extends IService<ChatDoc> {

    /**
     * chat
     *
     * @param searchText
     * @param isKeyWords
     * @return
     */
    List<ChatDocVo> chat(String searchText, Integer isKeyWords);

    /**
     * 根据关键词查询
     *
     * @param keyWordsId
     * @return
     */
    List<ChatDoc> listByKeyWords(Long keyWordsId);
}

