package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ProjectSeqMapper;
import com.lplb.modular.model.entity.ProjectSeq;
import com.lplb.modular.service.ProjectSeqService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 17:34:50
 * @Description（描述）：项目出现频次(ProjectSeq)表服务实现类
 */
@Service
@Transactional
public class ProjectSeqServiceImpl extends ServiceImpl<ProjectSeqMapper, ProjectSeq> implements ProjectSeqService {

}

