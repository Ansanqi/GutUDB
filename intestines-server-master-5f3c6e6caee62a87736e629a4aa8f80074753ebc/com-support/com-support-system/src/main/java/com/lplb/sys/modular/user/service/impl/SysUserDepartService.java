package com.lplb.sys.modular.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.factory.PageFactory;
import com.lplb.core.pojo.page.PageResult;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.sys.modular.user.entity.SysUserDepart;
import com.lplb.sys.modular.user.mapper.SysUserDepartMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (SysUserDepart)表服务实现类
 *
 * @author Leiziyu
 * @since 2022-04-25 15:09:52
 */
@Service("sysUserDepartService")
public class SysUserDepartService extends ServiceImpl<SysUserDepartMapper, SysUserDepart> {

    /**
     * 获取部门下的用户列表
     * @return
     */
    public Object getUserPageForDepart(Long deptId,String keyword,Long orgId){
        Page<T> page = PageFactory.defaultPage();
        Page<Map<String,Object>> deptUserPage = this.getBaseMapper().getUserPageForDepart(page,deptId,keyword,orgId);
        return ResponseData.success(new PageResult<>(deptUserPage));
    }

    /**
     * 保存用户部门
     * @param id
     * @param deptIdList
     */
    public void saveUserDepart(Long id, List<String> deptIdList) {
        List<SysUserDepart> saveList = new ArrayList<>();
        SysUserDepart sysUserDepart;
        for (String deptId : deptIdList) {
            sysUserDepart = new SysUserDepart();
            sysUserDepart.setUserId(id);
            sysUserDepart.setDepId(Long.parseLong(deptId));
            saveList.add(sysUserDepart);
        }
        if (!saveList.isEmpty()) {
            this.saveOrUpdateBatch(saveList);
        }
    }
}

