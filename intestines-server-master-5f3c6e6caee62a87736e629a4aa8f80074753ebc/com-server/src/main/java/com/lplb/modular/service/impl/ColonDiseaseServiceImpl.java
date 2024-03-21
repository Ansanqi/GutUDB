package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ColonDiseaseMapper;
import com.lplb.modular.model.entity.ColonDisease;
import com.lplb.modular.service.ColonDiseaseService;
import org.springframework.stereotype.Service;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Colon Disease（肠道疾病）(ColonDisease)表服务实现类
 */
@Service
public class ColonDiseaseServiceImpl extends ServiceImpl<ColonDiseaseMapper, ColonDisease> implements ColonDiseaseService {

    /**
     * 新增
     *
     * @param colonDisease
     * @return
     */
    @Override
    public Boolean insert(ColonDisease colonDisease) {
        return this.save(colonDisease);
    }
}

