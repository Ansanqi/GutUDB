package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.DiseaseSeqMapper;
import com.lplb.modular.model.entity.DiseaseSeq;
import com.lplb.modular.service.DiseaseSeqService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:42:38
 * @Description（描述）：疾病出现频次(DiseaseSeq)表服务实现类
 */
@Service
@Transactional
public class DiseaseSeqServiceImpl extends ServiceImpl<DiseaseSeqMapper, DiseaseSeq> implements DiseaseSeqService {

}

