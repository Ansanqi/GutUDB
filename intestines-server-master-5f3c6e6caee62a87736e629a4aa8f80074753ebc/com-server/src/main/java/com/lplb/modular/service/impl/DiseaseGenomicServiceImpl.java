package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.DiseaseGenomicMapper;
import com.lplb.modular.model.entity.DiseaseGenomic;
import com.lplb.modular.service.DiseaseGenomicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：疾病基因组(DiseaseGenomic)表服务实现类
 */
@Service
@Transactional
public class DiseaseGenomicServiceImpl extends ServiceImpl<DiseaseGenomicMapper, DiseaseGenomic> implements DiseaseGenomicService {

}

