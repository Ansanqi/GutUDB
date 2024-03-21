package com.lplb.sys.modular.org.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lplb.core.exception.ServiceException;
import com.lplb.sys.modular.org.entity.SysOrgInfo;
import com.lplb.sys.modular.org.mapper.SysOrgInfoMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 机构信息表(SysOrgInfo)表服务实现类
 *
 * @author Ray
 * @since 2021-12-13 14:07:40
 */
@Service
public class SysOrgInfoService extends ServiceImpl<SysOrgInfoMapper, SysOrgInfo> {

    /**
     * 获取机构绑定的开放平台应用ID
     * @return
     */
    public SysOrgInfo getOrgInfo(Long orgId){
        LambdaQueryWrapper<SysOrgInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrgInfo::getOrgUuid,orgId);
        queryWrapper.last("limit 1");
        SysOrgInfo orgInfo = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(orgInfo)) {
            throw new ServiceException(500,"当前机构未绑定开放平台应用，无法进行服务调用");
        }
        return orgInfo;
    }
}
