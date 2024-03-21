package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ProfilesMapper;
import com.lplb.modular.model.entity.Profiles;
import com.lplb.modular.service.ProfilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Profiles（图样）(Profiles)表服务实现类
 */
@Service
@Transactional
public class ProfilesServiceImpl extends ServiceImpl<ProfilesMapper, Profiles> implements ProfilesService {

}

