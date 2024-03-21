package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.TissueCellLineSeqMapper;
import com.lplb.modular.model.entity.TissueCellLineSeq;
import com.lplb.modular.service.TissueCellLineSeqService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-31 10:27:16
 * @Description（描述）：组织/细胞系出现频次(TissueCellLineSeq)表服务实现类
 */
@Service
@Transactional
public class TissueCellLineSeqServiceImpl extends ServiceImpl<TissueCellLineSeqMapper, TissueCellLineSeq> implements TissueCellLineSeqService {

}

