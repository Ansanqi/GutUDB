package com.lplb.sys.modular.org.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lplb.sys.modular.org.entity.SysOrgRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构关联表(SysOrgRelation)表服务接口
 *
 * @author Ray
 * @since 2021-12-24 13:16:02
 */
public interface SysOrgRelationMapper extends BaseMapper<SysOrgRelation> {

    /**
     * 获取关联机构信息
     * @param page
     * @param orgId
     * @return
     */
    List<Map<String, Object>> getOrgRelationPage(@Param("page") Page<Object> page, @Param("orgId") Long orgId);

    /**
     * 获取机构关联的所有机构ID
     * @param orgId
     * @return
     */
    List<Long> getOrgRelationList(Long orgId);
}
