package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.GeneSeqMapper;
import com.lplb.modular.model.entity.GeneSeq;
import com.lplb.modular.service.GeneSeqService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 18:41:03
 * @Description（描述）：基因出现频次(GeneSeq)表服务实现类
 */
@Service
@Transactional
public class GeneSeqServiceImpl extends ServiceImpl<GeneSeqMapper, GeneSeq> implements GeneSeqService {

}

