package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ChatDocKeyWords;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc_key_words(ChatDocKeyWords)表服务接口
 */
public interface ChatDocKeyWordsService extends IService<ChatDocKeyWords> {

    /**
     * 热词添加
     *
     * @param keyWords
     * @return
     */
    Boolean addKeyWords(String keyWords);

    /**
     * 查询关键词
     *
     * @param searchText
     * @return
     */
    ChatDocKeyWords getByKeyWords(String searchText);

    /**
     * top10
     *
     * @return
     */
    List<ChatDocKeyWords> top10();
}

