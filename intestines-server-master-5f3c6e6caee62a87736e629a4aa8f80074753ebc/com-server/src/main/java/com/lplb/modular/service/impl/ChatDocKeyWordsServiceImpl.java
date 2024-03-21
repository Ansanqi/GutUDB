package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ChatDocKeyWordsMapper;
import com.lplb.modular.model.entity.ChatDocKeyWords;
import com.lplb.modular.service.ChatDocKeyWordsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc_key_words(ChatDocKeyWords)表服务实现类
 */
@Service
public class ChatDocKeyWordsServiceImpl extends ServiceImpl<ChatDocKeyWordsMapper, ChatDocKeyWords> implements ChatDocKeyWordsService {

    /**
     * 热词添加
     *
     * @param keyWords
     * @return
     */
    @Override
    public Boolean addKeyWords(String keyWords) {
        ChatDocKeyWords words = new ChatDocKeyWords();
        words.setKeyWords(keyWords);
        words.setSearchCount(0);
        return this.save(words);
    }

    /**
     * 查询关键词
     *
     * @param searchText
     * @return
     */
    @Override
    public ChatDocKeyWords getByKeyWords(String searchText) {
        LambdaQueryWrapper<ChatDocKeyWords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatDocKeyWords::getKeyWords, searchText);
        wrapper.orderByDesc(ChatDocKeyWords::getSearchCount);
        return this.getOne(wrapper);
    }

    /**
     * top10
     *
     * @return
     */
    @Override
    public List<ChatDocKeyWords> top10() {
        Page<ChatDocKeyWords> page = new Page<>(1, 10);
        LambdaQueryWrapper<ChatDocKeyWords> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ChatDocKeyWords::getSearchCount);

        page = this.page(page, wrapper);
        return page.getRecords();
    }
}

