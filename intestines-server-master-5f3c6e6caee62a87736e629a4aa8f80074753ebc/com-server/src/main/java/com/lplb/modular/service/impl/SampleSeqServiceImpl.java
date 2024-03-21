package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SampleSeqMapper;
import com.lplb.modular.model.entity.SampleSeq;
import com.lplb.modular.service.SampleSeqService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:05:24
 * @Description（描述）：样本出现频次(SampleSeq)表服务实现类
 */
@Service
@Transactional
public class SampleSeqServiceImpl extends ServiceImpl<SampleSeqMapper, SampleSeq> implements SampleSeqService {

}

