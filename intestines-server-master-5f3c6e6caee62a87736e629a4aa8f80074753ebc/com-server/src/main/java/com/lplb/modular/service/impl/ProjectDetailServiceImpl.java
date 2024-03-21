package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.model.entity.GeneExpressionData;
import com.lplb.modular.model.entity.GeoInformation;
import com.lplb.modular.model.vo.ProjectDetailVo;
import com.lplb.modular.service.GeneExpressionDataService;
import com.lplb.modular.service.GeoInformationService;
import com.lplb.modular.service.ProjectDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-03 22:31
 * @Description（描述）：ProjectDetailServiceImpl
 */
@Service
@Slf4j
@Transactional
public class ProjectDetailServiceImpl implements ProjectDetailService {

    @Resource
    private GeoInformationService geoInformationService;
    @Resource
    private GeneExpressionDataService geneExpressionDataService;

    @Override
    public ProjectDetailVo projectDetailByProjectNo(String projectNo) {
        // 根据项目编号查询in_geo_information表
        GeoInformation information = geoInformationService.getByProject(projectNo);
        if (ObjectUtils.isEmpty(information)) {
            throw new ServiceException(500, "项目信息不存在");
        }

        // 类型转换
        ProjectDetailVo result = BeanConvertUtil.convert(information, ProjectDetailVo.class);

        // 查询该项目下所有的sample列表
        LambdaQueryWrapper<GeneExpressionData> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(GeneExpressionData::getBodySite);
        wrapper.eq(GeneExpressionData::getProject, projectNo);
        wrapper.groupBy(GeneExpressionData::getBodySite);
        wrapper.orderByAsc(GeneExpressionData::getBodySite);
        List<GeneExpressionData> datas = geneExpressionDataService.list(wrapper);
        // 取出Body site，作为sample列表
        List<String> samples = Collections.EMPTY_LIST;
        if (ObjectUtils.isNotEmpty(datas) && ObjectUtils.isNotEmpty(datas.get(0)) && ObjectUtils.isNotEmpty(datas.get(0).getBodySite())) {
            samples = datas.stream().map(item -> item.getBodySite()).collect(Collectors.toList());
        }
        result.setSamples(samples);
        return result;
    }
}
