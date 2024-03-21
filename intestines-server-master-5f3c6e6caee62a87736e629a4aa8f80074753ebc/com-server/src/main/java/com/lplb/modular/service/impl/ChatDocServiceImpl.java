package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ChatDocMapper;
import com.lplb.modular.model.entity.ChatDoc;
import com.lplb.modular.model.entity.ChatDocKeyWords;
import com.lplb.modular.model.vo.ChatDocVo;
import com.lplb.modular.service.ChatDocKeyWordsService;
import com.lplb.modular.service.ChatDocService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 16:48:21
 * @Description（描述）：Chatdoc(ChatDoc)表服务实现类
 */
@Service
public class ChatDocServiceImpl extends ServiceImpl<ChatDocMapper, ChatDoc> implements ChatDocService {

    @Resource
    private ChatDocKeyWordsService chatDocKeyWordsService;

    /**
     * chat
     *
     * @param searchText
     * @param isKeyWords
     * @return
     */
    @Override
    public List<ChatDocVo> chat(String searchText, Integer isKeyWords) {
        // 返回结果
        List<ChatDocVo> result = new ArrayList<>();
        // 是关键词，则做精准匹配，否则作分词匹配
        if (ObjectUtils.isNotEmpty(isKeyWords) && 1 == isKeyWords.intValue()) {
            if (ObjectUtils.isNotEmpty(searchText)) {
                // 通过关键词查询分类
                ChatDocKeyWords keyWords = chatDocKeyWordsService.getByKeyWords(searchText);
                if (ObjectUtils.isNotEmpty(keyWords)) {
                    // 关键词搜索次数加1
                    Integer searchCount = keyWords.getSearchCount() + 1;
                    keyWords.setSearchCount(searchCount);
                    chatDocKeyWordsService.updateById(keyWords);
                    // 查询返回结果集合
                    List<ChatDoc> list = this.listByKeyWords(keyWords.getId());
                    ChatDocVo vo = new ChatDocVo();
                    vo.setKeyWords(searchText);
                    vo.setChatDocs(list);
                    result.add(vo);
                    return result;
                } else {
                    return Collections.EMPTY_LIST;
                }
            } else {
                return Collections.EMPTY_LIST;
            }
        }
        if (ObjectUtils.isEmpty(searchText)) {
            return Collections.EMPTY_LIST;
        }

        // 查询条件转全小写
        searchText = searchText.toLowerCase();
        // 以空格分词
        List<String> texts = new ArrayList<>(Arrays.asList(searchText.split(" ")));
        // 去掉空串，此为查询条件分词结果列表，通过该结果去匹配所有问题得关键词、问题、答案，只要包含两个该分词结果中得任意一个分词，都作为返回数据返回
        texts = texts.stream().filter(text -> ObjectUtils.isNotEmpty(text)).collect(Collectors.toList());
        // 循环所有关键词
        List<String> finalTexts = texts;

        // 查询包含所有分词得所有关键词
        LambdaQueryWrapper<ChatDocKeyWords> keyWrapper = new LambdaQueryWrapper<>();
        // 循环分词
        finalTexts.forEach(item -> {
            if (0 == finalTexts.indexOf(item)) {
                keyWrapper.like(ChatDocKeyWords::getKeyWords, item);
            } else {
                keyWrapper.or().like(ChatDocKeyWords::getKeyWords, item);
            }

        });
        List<ChatDocKeyWords> keyWordsList = chatDocKeyWordsService.list(keyWrapper);
        // 关键词ID集合
        List<Long> keywordsIds = keyWordsList.stream().map(i -> i.getId()).collect(Collectors.toList());

        // 查询所有问答结果
        LambdaQueryWrapper<ChatDoc> wrapper = new LambdaQueryWrapper<>();
        // 循环分词
        finalTexts.forEach(item -> {
            if (0 == finalTexts.indexOf(item)) {
                wrapper.like(ChatDoc::getQuestion, item)
                        .or()
                        .like(ChatDoc::getAnswer, item);
            } else {
                wrapper.or()
                        .like(ChatDoc::getQuestion, item)
                        .or()
                        .like(ChatDoc::getAnswer, item);
            }

        });
        if (ObjectUtils.isNotEmpty(keywordsIds)) {
            wrapper.or().in(ChatDoc::getKeyWordsId, keywordsIds);

        }
        wrapper.orderByAsc(ChatDoc::getOrderNo);

        List<ChatDoc> chatDocList = this.list(wrapper);
        if (ObjectUtils.isEmpty(chatDocList)) {
            return Collections.EMPTY_LIST;
        }

        // 循环，添加查询出来得结果到Map中
        Map<Long, List<ChatDoc>> map = new LinkedHashMap<>();
        chatDocList.forEach(item -> {
            Long key = item.getKeyWordsId();
            List<ChatDoc> value;
            if (map.containsKey(key)) {
                value = map.get(key);
            } else {
                value = new ArrayList<>();
            }
            value.add(item);
            map.put(key, value);
        });

        // 循环map，添加数据到结果中
        map.forEach((key, value) -> {
            ChatDocVo vo = new ChatDocVo();

            // 查询关键词
            ChatDocKeyWords keyWords = chatDocKeyWordsService.getById(key);
            vo.setKeyWords(keyWords.getKeyWords());
            vo.setChatDocs(value);
            result.add(vo);
        });


//        // 转全小写
//        searchText = searchText.toLowerCase();
//        // 以空格分词
//        List<String> texts = new ArrayList<>(Arrays.asList(searchText.split(" ")));
//        // 去掉空串
//        texts = texts.stream().filter(text -> ObjectUtils.isNotEmpty(text)).collect(Collectors.toList());
//
//        // 包含关键词
//        List<ChatDocKeyWords> keyWordss = new ArrayList<>();
//
//        // 查询所有关键词
//        List<ChatDocKeyWords> keyWordsList = chatDocKeyWordsService.list();
//
//        // 循环所有关键词
//        List<String> finalTexts = texts;
//        keyWordsList.forEach(keyWords -> {
//            // 循环搜索分词
//            finalTexts.forEach(text -> {
//                if (keyWords.getKeyWords().contains(text) && !keyWordss.contains(keyWords)) {
//                    keyWordss.add(keyWords);
//                }
//            });
//        });
//
//        if (ObjectUtils.isEmpty(keyWordss)) {
//            return null;
//        }
//
//        // 返回结果
//        List<ChatDocVo> result = new ArrayList<>();
//
//        // 循环查询
//        keyWordss.forEach(item -> {
//            ChatDocVo vo = new ChatDocVo();
//            vo.setKeyWords(item.getKeyWords());
//            // 精确匹配关键词对应的问答
//            LambdaQueryWrapper<ChatDoc> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(ChatDoc::getKeyWordsId, item.getId());
//            wrapper.orderByAsc(ChatDoc::getOrderNo);
//            List<ChatDoc> chatDocs = this.list(wrapper);
//            vo.setChatDocs(chatDocs);
//            result.add(vo);
//
//            // 关键词搜索次数加1
//            Integer searchCount = item.getSearchCount() + 1;
//            item.setSearchCount(searchCount);
//            chatDocKeyWordsService.updateById(item);
//        });
        return result;
    }

    /**
     * 根据关键词查询
     *
     * @param keyWordsId
     * @return
     */
    @Override
    public List<ChatDoc> listByKeyWords(Long keyWordsId) {
        LambdaQueryWrapper<ChatDoc> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatDoc::getKeyWordsId, keyWordsId);
        wrapper.orderByAsc(ChatDoc::getOrderNo);
        return this.list(wrapper);
    }
}

