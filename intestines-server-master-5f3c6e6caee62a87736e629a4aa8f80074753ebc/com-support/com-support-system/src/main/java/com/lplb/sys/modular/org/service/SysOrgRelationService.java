package com.lplb.sys.modular.org.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lplb.core.factory.PageFactory;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.modular.org.entity.SysOrgRelation;
import com.lplb.sys.modular.org.mapper.SysOrgRelationMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机构关联表(SysOrgRelation)表服务实现类
 *
 * @author Ray
 * @since 2021-12-24 13:16:02
 */
@Service
public class SysOrgRelationService extends ServiceImpl<SysOrgRelationMapper, SysOrgRelation> {

    /**
     * 获取关联机构信息
     * @param orgId
     * @return
     */
    public Object getOrgRelationPage(Long orgId) {
        List<Map<String,Object>> result = this.getBaseMapper().getOrgRelationPage(PageFactory.defaultPage(),orgId);
        return SuccessResponseData.success(result);
    }

    /**
     * 保存关联机构信息
     * @return
     */
    public Object saveOrgRelation(Long orgId,Long salveId){
        LambdaQueryWrapper<SysOrgRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrgRelation::getMasterOrgId,orgId);
        queryWrapper.eq(SysOrgRelation::getSalveOrgId,salveId);
        int count = this.count(queryWrapper);
        if(count==0){
            SysOrgRelation relation = new SysOrgRelation();
            relation.setMasterOrgId(orgId);
            relation.setSalveOrgId(salveId);
            this.saveOrUpdate(relation);
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrgRelation::getMasterOrgId,salveId);
        queryWrapper.eq(SysOrgRelation::getSalveOrgId,orgId);
        int countTwo = this.count(queryWrapper);
        if(countTwo==0){
            SysOrgRelation relation = new SysOrgRelation();
            relation.setMasterOrgId(salveId);
            relation.setSalveOrgId(orgId);
            this.saveOrUpdate(relation);
        }
        return SuccessResponseData.success();
    }

    /**
     * 删除关联机构信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Object deleteOrgRelation(Long relationId){
        SysOrgRelation masterOrg = this.getById(relationId);
        Long salveOrgId = masterOrg.getSalveOrgId();
        LambdaQueryWrapper<SysOrgRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOrgRelation::getMasterOrgId,salveOrgId);
        queryWrapper.eq(SysOrgRelation::getSalveOrgId,masterOrg.getMasterOrgId());
        queryWrapper.last("limit 1");
        SysOrgRelation salveOrg = this.getOne(queryWrapper);
        this.removeById(masterOrg);
        this.removeById(salveOrg);
        return SuccessResponseData.success();
    }

    /**
     * 获取机构的所有关联机构
     * @param orgId
     * @return
     */
    public List<Long> getOrgRelationList(Long orgId){
        List<Long> result = this.getBaseMapper().getOrgRelationList(orgId);
        if (ObjectUtil.isEmpty(result)) {
            result = new ArrayList<>();
        }
        result.add(orgId);
        return result;
    }
}
