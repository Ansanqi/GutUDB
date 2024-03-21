package com.lplb.sys.modular.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lplb.sys.modular.org.entity.SysOrgLeader;

import java.util.List;
import java.util.Map;

/**
 * 部门领导表(SysOrgLeader)表服务接口
 *
 * @author Leiziyu
 * @since 2021-09-17 13:20:59
 */
public interface SysOrgLeaderMapper extends BaseMapper<SysOrgLeader> {

    /**
     * 获取部门领导
     * @param orgId
     * @return
     */
    List<Long> getOrgLeader(Long orgId);

    List<Map<String,Object>> getOrgLeaderInfo(Long orgId);
}
