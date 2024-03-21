package com.lplb.sys.modular.depart.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.framework.util.FindsDepartsChildrenUtil;
import com.lplb.sys.core.consts.SystemCommonConstant;
import com.lplb.sys.modular.depart.entity.SysDepart;
import com.lplb.sys.modular.depart.mapper.SysDepartMapper;
import com.lplb.sys.modular.depart.model.SysDepartTreeModel;
import com.lplb.sys.modular.user.entity.SysUserDepart;
import com.lplb.sys.modular.user.service.impl.SysUserDepartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 组织机构表(SysDepart)表服务实现类
 *
 * @author Leiziyu
 * @since 2022-04-21 18:14:10
 */
@Service("sysDepartService")
public class SysDepartService extends ServiceImpl<SysDepartMapper, SysDepart> {


    @Resource
    SysUserDepartService userDepartService;

    /**
     * 获取部门树
     * @param orgId
     * @return
     */
    public Object getDepartTree(String ids,Long orgId,String keyword,boolean isSuperAdmin) {
        LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
        if(!isSuperAdmin){
            query.eq(SysDepart::getOrgId,orgId);
        }
        query.eq(SysDepart::getDelFlag, SystemCommonConstant.NOT_DELETED);
        if(ObjectUtil.isNotEmpty(ids)){
            query.in(true,SysDepart::getId, Arrays.asList(ids.split(",")));
        }
        if (ObjectUtil.isNotEmpty(keyword)) {
            query.and(q -> q.like(SysDepart::getDepartName,keyword).or().like(SysDepart::getOrgCode,keyword));
        }
        query.orderByAsc(SysDepart::getDepartOrder);
        List<SysDepart> list= this.list(query);
        List<SysDepartTreeModel> listResult = FindsDepartsChildrenUtil.wrapTreeDataToTreeList(list);
        return ResponseData.success(listResult);
    }

    /**
     * 保存部门
     * @param sysDepart
     * @param orgId
     * @param superAdmin
     */
    public Object saveDepart(SysDepart sysDepart, Long orgId, boolean superAdmin) {
        if (ObjectUtil.isEmpty(sysDepart.getId())) {
            LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
            query.eq(SysDepart::getOrgId,orgId);
            query.eq(SysDepart::getOrgCode,sysDepart.getOrgCode());
            query.eq(SysDepart::getDelFlag,SystemCommonConstant.NOT_DELETED);
            query.last("limit 1");
            SysDepart oriDepart = this.getOne(query);
            if (ObjectUtil.isNotEmpty(oriDepart)) {
                return ResponseData.error("部门编号重复");
            }
            sysDepart.setOrgId(orgId);
            this.saveOrUpdate(sysDepart);
        }else{
            SysDepart oriDepart = this.getById(sysDepart.getId());
            if (ObjectUtil.isEmpty(oriDepart)) {
                return ResponseData.error("部门不存在");
            }
            oriDepart.setMemo(sysDepart.getMemo());
            oriDepart.setDepartName(sysDepart.getDepartName());
            oriDepart.setStatus(sysDepart.getStatus());
            oriDepart.setDepartOrder(sysDepart.getDepartOrder());
            this.updateById(oriDepart);
        }
        return ResponseData.success("保存成功");
    }

    /**
     * 删除部门
     * @param departId
     * @param orgId
     * @return
     */
    public Object deleteDepart(Long departId, Long orgId) {
        SysDepart sysDepart = this.getById(departId);
        if (ObjectUtil.isEmpty(sysDepart)) {
            return ResponseData.error("部门不存在");
        }
        if(!sysDepart.getOrgId().equals(orgId)){
            return ResponseData.error(CommonConstant.TIP_NO_PERMISSION);
        }
        LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
        query.eq(SysDepart::getParentId,departId);
        query.eq(SysDepart::getDelFlag,SystemCommonConstant.NOT_DELETED);
        int count = this.count(query);
        if(count>0){
            return ResponseData.error("该部门下存在子部门，请先删除子部门");
        }
        LambdaQueryWrapper<SysUserDepart> udQuery = new LambdaQueryWrapper<>();
        udQuery.eq(SysUserDepart::getDepId,departId);
        int userCount = userDepartService.count(udQuery);
        if(userCount>0){
            return ResponseData.error("该部门下存在用户，请先移除该部门下的用户");
        }
        sysDepart.setDelFlag(SystemCommonConstant.DELETED);
        this.saveOrUpdate(sysDepart);
        return ResponseData.successMsg("部门删除成功");
    }

    /**
     * 删除部门用户关联
     * @param deptId
     * @param rIds
     * @param orgId
     * @return
     */
    public Object deleteDepartUser(Long deptId, String rIds, Long orgId) {
        SysDepart sysDepart = this.getById(deptId);
        if (ObjectUtil.isEmpty(sysDepart)) {
            return ResponseData.error("部门不存在");
        }
        if (!sysDepart.getOrgId().equals(orgId)) {
            return ResponseData.error(CommonConstant.TIP_NO_PERMISSION);
        }
        List<String> delList = new ArrayList<>();
        if(rIds.contains(StrUtil.COMMA)){
            delList.addAll(Arrays.asList(rIds.split(",")));
        }else{
            delList.add(rIds);
        }
        if (!delList.isEmpty()) {
            userDepartService.removeByIds(delList);
        }
        return ResponseData.successMsg("部门成员移除成功");
    }

    /**
     * 批量获取用户所在部门信息
     * @param userArr
     * @param orgId
     * @return
     */
    public Object getUserDepartInfo(JSONArray userArr,Long orgId) {
        List<Long> userList = userArr.toList(Long.class);
        return ResponseData.success(userDepartService.getBaseMapper().getUserDepartInfo(userList,orgId));
    }
}

