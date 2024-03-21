package com.lplb.sys.modular.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lplb.sys.modular.user.entity.SysUserDepart;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * (SysUserDepart)表数据库访问层
 *
 * @author Leiziyu
 * @since 2022-04-25 15:09:52
 */
public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {

    /**
     * 获取部门下的用户列表
     * @param page
     * @param deptId
     * @param orgId
     * @return
     */
    Page<Map<String,Object>> getUserPageForDepart(@Param("page") Page<T> page, @Param("deptId") Long deptId, @Param("keyword") String keyword, @Param("orgId") Long orgId);

    /**
     * 删除用户部门关联信息
     * @param userId 用户ID
     */
    void deleteUserDepart(Long userId);

    /**
     * 批量获取用户部门信息
     * @param userList
     * @return
     */
    List<Map<String,Object>> getUserDepartInfo(@Param("userList") List<Long> userList, @Param("orgId") Long orgId);
}

