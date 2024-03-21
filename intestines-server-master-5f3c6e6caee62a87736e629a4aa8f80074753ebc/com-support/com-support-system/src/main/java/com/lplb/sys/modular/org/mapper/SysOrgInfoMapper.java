package com.lplb.sys.modular.org.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.sys.modular.org.entity.SysOrgInfo;

/**
 * 机构信息表(SysOrgInfo)表服务接口
 *
 * @author Ray
 * @since 2021-12-13 14:07:40
 */
public interface SysOrgInfoMapper extends BaseMapper<SysOrgInfo> {

    /**
     * 获取机构信息
     * @param orgId
     * @return
     */
    SysOrgInfo getOrgInfo(Long orgId);

    /**
     * 删除开放平台授权机构的信息
     */
    void deleteByOpenId(Long openAppId);

}
