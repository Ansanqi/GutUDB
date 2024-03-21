package com.lplb.sys.modular.depart.controller;

import cn.hutool.json.JSONArray;
import com.lplb.core.context.login.LoginContext;
import com.lplb.sys.modular.depart.entity.SysDepart;
import com.lplb.sys.modular.depart.service.SysDepartService;
import com.lplb.sys.modular.user.service.impl.SysUserDepartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *   @author Ray-zy
 *   @since 2022/4/21 18:13
 **/
@RestController
@RequestMapping("/system/depart")
public class SysDepartController {

    @Resource
    SysDepartService sysDepartService;

    @Resource
    SysUserDepartService userDepartService;

    @Resource
    LoginContext loginContext;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/tree")
    public Object departTree(String ids,String keyword){
        return sysDepartService.getDepartTree(ids,loginContext.getSysLoginUserOrgId(),keyword,loginContext.isSuperAdmin());
    }

    /**
     * 保存部门
     * @return
     */
    @PostMapping("/save")
    public Object saveDepart(@RequestBody SysDepart sysDepart){
        return sysDepartService.saveDepart(sysDepart,loginContext.getSysLoginUserOrgId(),loginContext.isSuperAdmin());
    }

    /**
     * 删除部门
     * @return
     */
    @PostMapping("/delete")
    public Object deleteDepart(Long departId) {
        return sysDepartService.deleteDepart(departId,loginContext.getSysLoginUserOrgId());
    }

    /**
     * 获取部门下的用户信息
     * @return
     */
    @GetMapping("/user_page")
    public Object getDepartUserPage(@RequestParam Long deptId,String keyword){
        return userDepartService.getUserPageForDepart(deptId,keyword,loginContext.getSysLoginUserOrgId());
    }


    /**
     * 删除部门下的用户
     * @param deptId 部门ID
     * @param rIds 部门用户关联键
     * @return
     */
    @PostMapping("/user_delete")
    public Object deleteDepartUser(Long deptId,String rIds){
        return sysDepartService.deleteDepartUser(deptId,rIds,loginContext.getSysLoginUserOrgId());
    }

    /**
     * 批量获取用户所在部门信息
     * @param userArr
     * @return
     */
    @PostMapping("/user_depart")
    public Object getUserDepartInfo(@RequestBody JSONArray userArr){
        return sysDepartService.getUserDepartInfo(userArr,loginContext.getSysLoginUserOrgId());
    }

}
