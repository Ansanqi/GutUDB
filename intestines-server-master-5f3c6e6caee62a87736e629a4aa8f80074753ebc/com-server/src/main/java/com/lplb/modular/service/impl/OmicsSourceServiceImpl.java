package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.OmicsSourceMapper;
import com.lplb.modular.model.entity.OmicsSource;
import com.lplb.modular.service.OmicsSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:37
 * @Description（描述）：来源(OmicsSource)表服务实现类
 */
@Service
@Transactional
public class OmicsSourceServiceImpl extends ServiceImpl<OmicsSourceMapper, OmicsSource> implements OmicsSourceService {

}

