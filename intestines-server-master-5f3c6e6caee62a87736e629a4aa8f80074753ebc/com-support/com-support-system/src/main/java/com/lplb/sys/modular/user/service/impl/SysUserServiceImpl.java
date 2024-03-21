/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package com.lplb.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.consts.DictCodeConstant;
import com.lplb.core.consts.SymbolConstant;
import com.lplb.core.context.constant.ConstantContextHolder;
import com.lplb.core.context.login.LoginContextHolder;
import com.lplb.core.enums.CommonStatusEnum;
import com.lplb.core.exception.PermissionException;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.exception.enums.PermissionExceptionEnum;
import com.lplb.core.exception.enums.StatusExceptionEnum;
import com.lplb.core.factory.PageFactory;
import com.lplb.core.pojo.page.PageResult;
import com.lplb.core.pojo.response.ErrorResponseData;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.core.util.DateTimeUtil;
import com.lplb.core.util.PoiUtil;
import com.lplb.framework.poi.ExcelTemplateUtil;
import com.lplb.framework.util.EncryptUtil;
import com.lplb.sys.core.enums.AdminTypeEnum;
import com.lplb.sys.modular.dict.service.SysDictDataService;
import com.lplb.sys.modular.emp.entity.SysEmp;
import com.lplb.sys.modular.emp.param.SysEmpParam;
import com.lplb.sys.modular.emp.result.SysEmpInfo;
import com.lplb.sys.modular.emp.service.SysEmpService;
import com.lplb.sys.modular.file.enums.SysFileInfoExceptionEnum;
import com.lplb.sys.modular.file.service.SysFileInfoService;
import com.lplb.sys.modular.org.entity.SysOrg;
import com.lplb.sys.modular.org.service.SysOrgService;
import com.lplb.sys.modular.role.entity.SysRole;
import com.lplb.sys.modular.user.entity.SysUser;
import com.lplb.sys.modular.user.entity.SysUserRole;
import com.lplb.sys.modular.user.enums.SysUserExceptionEnum;
import com.lplb.sys.modular.user.factory.SysUserFactory;
import com.lplb.sys.modular.user.mapper.SysUserMapper;
import com.lplb.sys.modular.user.param.SysUserParam;
import com.lplb.sys.modular.user.result.SysUserResult;
import com.lplb.sys.modular.user.service.SysUserDataScopeService;
import com.lplb.sys.modular.user.service.SysUserRoleService;
import com.lplb.sys.modular.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 系统用户service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/3/11 17:49
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysEmpService sysEmpService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private SysFileInfoService sysFileInfoService;

    @Resource
    private SysDictDataService sysDictDataService;

    @Resource
    private SysUserRoleService userRoleService;

    @Resource
    private SysUserDepartService userDepartService;

    @Resource
    private SysOrgService orgService;

    @Override
    public SysUser getUserByCount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        return this.getOne(queryWrapper);
    }

    @Override
    public Object page(String account,String name,Long orgId) {
        Page<Map<String,Object>> result = this.baseMapper.userPage(PageFactory.defaultPage(),account,name,orgId);
        for (Map<String,Object> item : result.getRecords()) {
            String sex = item.get("sex").toString();
            item.put("sexName", sex.equals("1") ?"男": sex.equals("2") ?"女":"未知");
            List<SysRole> roles = userRoleService.getUserRole(Long.parseLong(item.get("id").toString()));
            item.put("roles",roles);
        }
        return new PageResult<>(result);
    }

    @Override
    public List<SysUserResult> userList(SysUserParam sysUserParam) {
        QueryWrapper<SysUserResult> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据关键字模糊查询（姓名，账号，手机号）
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchValue())) {
                queryWrapper.and(q -> q.like("sys_user.account", sysUserParam.getSearchValue())
                        .or().like("sys_user.name", sysUserParam.getSearchValue())
                        .or().like("sys_user.phone", sysUserParam.getSearchValue()));
            }
            //根据员工所属机构查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getSysEmpParam())) {
                SysEmpParam sysEmpParam = sysUserParam.getSysEmpParam();
                if (ObjectUtil.isNotEmpty(sysEmpParam.getOrgId())) {
                    //查询属于该机构的，或该机构下级所有的人员
                    queryWrapper.and(q -> q.eq("sys_emp.org_id", sysEmpParam.getOrgId())
                            .or().like("sys_org.pids", sysEmpParam.getOrgId()));
                }
            }
            //根据状态查询（0正常 1停用），删除的不会展示在列表
            if (ObjectUtil.isNotEmpty(sysUserParam.getSearchStatus())) {
                queryWrapper.eq("sys_user.status", sysUserParam.getSearchStatus());
            }
        }
        //查询非删除状态，排除超级管理员
        queryWrapper.ne("sys_user.status", CommonStatusEnum.DELETED.getCode())
                .ne("sys_user.admin_type", AdminTypeEnum.SUPER_ADMIN.getCode());
//        //如果是超级管理员则获取所有用户，否则只获取其数据范围的用户
//        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
//        if (!superAdmin) {
//            List<Long> dataScope = sysUserParam.getDataScope();
//            if (ObjectUtil.isEmpty(dataScope)) {
//                return new ArrayList<>();
//            } else {
//                Set<Long> dataScopeSet = CollectionUtil.newHashSet(dataScope);
//                queryWrapper.in("cms.sys_emp.org_id", dataScopeSet);
//            }
//        }
        return this.baseMapper.list(queryWrapper);
    }

    @Override
    public List<SysUserResult> userSelectList(SysUserParam sysUserParam) {
        return this.getBaseMapper().getUserSelectList(sysUserParam);
    }

    @Override
    public List<Dict> list(SysUserParam sysUserParam) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据账号，姓名模糊查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getAccount())) {
                queryWrapper.and(i -> i.like(SysUser::getAccount, sysUserParam.getAccount())
                        .or().like(SysUser::getName, sysUserParam.getAccount()));
            }
        }
        //查询正常状态，排除超级管理员
        queryWrapper.eq(SysUser::getStatus, CommonStatusEnum.ENABLE.getCode())
                .ne(SysUser::getAdminType, AdminTypeEnum.SUPER_ADMIN.getCode());
        this.list(queryWrapper).forEach(sysUser -> {
            Dict dict = Dict.create();
            dict.put("id", sysUser.getId().toString());
            dict.put("firstName", sysUser.getName() + SymbolConstant.LEFT_SQUARE_BRACKETS
                    + sysUser.getAccount() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            dictList.add(dict);
        });
        return dictList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysUserParam sysUserParam) {
        checkParam(sysUserParam, false);
//        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
//        //如果登录用户不是超级管理员，则进行数据权限校验
//        if (!superAdmin) {
//            List<Long> dataScope = sysUserParam.getDataScope();
//            //获取添加的用户的所属机构
//            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
//            //数据范围为空
//            if (ObjectUtil.isEmpty(dataScope)) {
//                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
//            } else if (!dataScope.contains(orgId)) {
//                //所添加的用户的所属机构不在自己的数据范围内
//                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
//            }
//        }
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserParam, sysUser);
        SysUserFactory.fillAddCommonUserInfo(sysUser);
        sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword(), BCrypt.gensalt()));
        this.save(sysUser);
        Long sysUserId = sysUser.getId();
//        //增加员工信息
        SysEmpParam sysEmpParam = new SysEmpParam();
        sysEmpParam.setId(sysUserId);
        sysEmpParam.setOrgId(sysUserParam.getOrgId());
        sysEmpService.addOrUpdate(sysEmpParam);

        //部门信息
        if (ObjectUtil.isNotEmpty(sysUserParam.getDeptIds())) {
            List<String> deptIdList = new ArrayList<>();
            if (sysUserParam.getDeptIds().contains(StrUtil.COMMA)) {
                deptIdList.addAll(Arrays.asList(sysUserParam.getDeptIds().split(",")));
            }else{
                deptIdList.add(sysUserParam.getDeptIds());
            }
            userDepartService.saveUserDepart(sysUser.getId(),deptIdList);
        }

        //保存用户角色信息
        List<SysUserRole> roleSaveList = new ArrayList<>();
        for (Long roleId : sysUserParam.getRoleIds()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUserId);
            userRole.setRoleId(roleId);
            roleSaveList.add(userRole);
        }
        userRoleService.saveOrUpdateBatch(roleSaveList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //不能删除超级管理员
        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要删除的用户的所属机构
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要删除的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        sysUser.setStatus(CommonStatusEnum.DELETED.getCode());
        this.updateById(sysUser);
        Long id = sysUser.getId();
        //删除该用户对应的员工表信息
        sysEmpService.deleteEmpInfoByUserId(id);

        //删除该用户对应的用户-角色表关联信息
        sysUserRoleService.deleteUserRoleListByUserId(id);

        //删除该用户对应的用户-数据范围表关联信息
        sysUserDataScopeService.deleteUserDataScopeListByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        checkParam(sysUserParam, true);
//        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
//        //如果登录用户不是超级管理员，则进行数据权限校验
//        if (!superAdmin) {
//            List<Long> dataScope = sysUserParam.getDataScope();
//            //获取要编辑的用户的所属机构
//            Long orgId = sysUserParam.getSysEmpParam().getOrgId();
//            //数据范围为空
//            if (ObjectUtil.isEmpty(dataScope)) {
//                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
//            } else if (!dataScope.contains(orgId)) {
//                //所要编辑的用户的所属机构不在自己的数据范围内
//                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
//            }
//        }

        BeanUtil.copyProperties(sysUserParam, sysUser);
        //不能修改状态，用修改状态接口修改状态
        sysUser.setStatus(null);
        //设置密码
//        SysUserFactory.fillBaseUserInfo(sysUser);
        this.updateById(sysUser);
        Long sysUserId = sysUser.getId();

        //部门信息
        userDepartService.getBaseMapper().deleteUserDepart(sysUserId);
        if (ObjectUtil.isNotEmpty(sysUserParam.getDeptIds())) {
            List<String> deptIdList = new ArrayList<>();
            if (sysUserParam.getDeptIds().contains(StrUtil.COMMA)) {
                deptIdList.addAll(Arrays.asList(sysUserParam.getDeptIds().split(",")));
            }else{
                deptIdList.add(sysUserParam.getDeptIds());
            }
            userDepartService.saveUserDepart(sysUser.getId(),deptIdList);
        }


        sysUserRoleService.deleteUserRoleListByUserId(sysUserId);
        List<SysUserRole> roleSaveList = new ArrayList<>();
        for (Long roleId : sysUserParam.getRoleIds()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUserId);
            userRole.setRoleId(roleId);
            roleSaveList.add(userRole);
        }
        userRoleService.saveOrUpdateBatch(roleSaveList);
        if (ObjectUtil.isNotEmpty(sysUserParam.getOrgId())) {
            //编辑员工信息
            SysEmpParam sysEmpParam = new SysEmpParam();
            sysEmpParam.setOrgId(sysUserParam.getOrgId());
            sysEmpParam.setId(sysUserId);
            sysEmpService.addOrUpdate(sysEmpParam);
        }
    }

    @Override
    public SysUserResult detail(SysUserParam sysUserParam) {
        SysUserResult sysUserResult = new SysUserResult();
        //获取系统用户
        SysUser sysUser = this.querySysUser(sysUserParam);
        BeanUtil.copyProperties(sysUser, sysUserResult);
        //获取对应员工信息
        SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
        //设置员工信息
        sysUserResult.setSysEmpInfo(sysEmpInfo);
        return sysUserResult;
    }

    @Override
    public ResponseData changeStatus(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //不能修改超级管理员状态
        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getId();

        Integer status = sysUserParam.getStatus();
        //校验状态在不在枚举值里
        CommonStatusEnum.validateStatus(status);

        //更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, id)
                .and(i -> i.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode()))
                .set(SysUser::getStatus, status);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new ServiceException(StatusExceptionEnum.UPDATE_STATUS_ERROR);
        }
        return SuccessResponseData.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object grantRole(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要授权角色的用户的所属机构
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要授权角色的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        sysUserRoleService.grantRole(sysUserParam);
        return SuccessResponseData.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantData(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        boolean superAdmin = LoginContextHolder.me().isSuperAdmin();
        //如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {
            List<Long> dataScope = sysUserParam.getDataScope();
            //获取要授权数据的用户的所属机构
            SysEmpInfo sysEmpInfo = sysEmpService.getSysEmpInfo(sysUser.getId());
            Long orgId = sysEmpInfo.getOrgId();
            //数据范围为空
            if (ObjectUtil.isEmpty(dataScope)) {
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            } else if (!dataScope.contains(orgId)) {
                //所要授权数据的用户的所属机构不在自己的数据范围内
                throw new PermissionException(PermissionExceptionEnum.NO_PERMISSION_OPERATE);
            }
        }
        sysUserDataScopeService.grantData(sysUserParam);
    }

    @Override
    public ResponseData updateInfo(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        BeanUtil.copyProperties(sysUserParam, sysUser);
        this.updateById(sysUser);
        return new SuccessResponseData();
    }

    @Override
    public void updatePwd(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        //新密码与原密码相同
        if (sysUserParam.getNewPassword().equals(sysUserParam.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }
        //原密码错误
        if (!BCrypt.checkpw(sysUserParam.getPassword(), sysUser.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_ERROR);
        }
        sysUser.setPassword(BCrypt.hashpw(sysUserParam.getNewPassword(), BCrypt.gensalt()));
        sysUser.setPwdTime(DateTimeUtil.getNow());
        sysUser.setPwdStatus("1");
        this.updateById(sysUser);
    }

    @Override
    public List<Long> getUserDataScopeIdList(Long userId, Long orgId) {
        Set<Long> userDataScopeIdSet = CollectionUtil.newHashSet();
        if (ObjectUtil.isAllNotEmpty(userId, orgId)) {

            //获取该用户对应的数据范围集合
            List<Long> userDataScopeIdListForUser = sysUserDataScopeService.getUserDataScopeIdList(userId);

            //获取该用户的角色对应的数据范围集合
            List<Long> userDataScopeIdListForRole = sysUserRoleService.getUserRoleDataScopeIdList(userId, orgId);

            userDataScopeIdSet.addAll(userDataScopeIdListForUser);
            userDataScopeIdSet.addAll(userDataScopeIdListForRole);
        }
        return CollectionUtil.newArrayList(userDataScopeIdSet);
    }

    @Override
    public String getNameByUserId(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser.getName();
    }

    @Override
    public String getNameByUser(Long userId) {
        SysUser sysUser = this.getById(userId);
        return ObjectUtil.isNotEmpty(sysUser)?sysUser.getName():"";
    }

    @Override
    public String getNamesByUsers(String userIds) {
        if (ObjectUtil.isEmpty(userIds)) {
            return "";
        }
        List<String> users = new ArrayList<>();
        if(userIds.contains(",")){
            users.addAll(Arrays.asList(userIds.split(",")));
        }else{
            users.add(userIds);
        }
        List<String> userNames = new ArrayList<>();
        for (String user : users) {
            SysUser sysUser = this.getById(user);
            if (ObjectUtil.isNotEmpty(sysUser)) {
                userNames.add(sysUser.getName());
            }
        }
        return StringUtils.join(userNames,",");
    }
    @Override
    public List<Long> ownRole(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        return sysUserRoleService.getUserRoleIdList(sysUser.getId());
    }

    @Override
    public List<Long> ownData(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        return sysUserDataScopeService.getUserDataScopeIdList(sysUser.getId());
    }

    @Override
    public void resetPwd(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        sysUser.setPassword(BCrypt.hashpw(sysUserParam.getPassword(), BCrypt.gensalt()));
        this.updateById(sysUser);
    }

    @Override
    public void updateAvatar(SysUserParam sysUserParam) {
        SysUser sysUser = this.querySysUser(sysUserParam);
        Long avatar = sysUserParam.getAvatar();
        sysFileInfoService.assertFile(avatar);
        sysUser.setAvatar(avatar);
        this.updateById(sysUser);
    }

    @Override
    public void export(SysUserParam sysUserParam) {
        List<SysUser> list = this.list();
        PoiUtil.exportExcelWithStream("GunsUsers.xls", SysUser.class, list);
    }

    @Override
    public List<Dict> selector(SysUserParam sysUserParam) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysUserParam)) {
            //根据姓名模糊查询
            if (ObjectUtil.isNotEmpty(sysUserParam.getName())) {
                queryWrapper.like(SysUser::getName, sysUserParam.getName());
            }
        }
        //查询非删除状态，排除超级管理员
        queryWrapper.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode())
                .ne(SysUser::getAdminType, AdminTypeEnum.SUPER_ADMIN.getCode());
       this.list(queryWrapper).forEach(sysUser -> {
            Dict dict  = Dict.create();
            dict.put(CommonConstant.ID, sysUser.getId());
            dict.put(CommonConstant.NAME, sysUser.getName());
            dictList.add(dict);
       });
       return dictList;
    }

    /**
     * 校验参数，检查是否存在相同的账号
     *
     * @author xuyuxiang
     * @date 2020/3/27 16:04
     */
    private void checkParam(SysUserParam sysUserParam, boolean isExcludeSelf) {
        Long id = sysUserParam.getId();
        String account = sysUserParam.getAccount();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account)
                .ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        //是否排除自己，如果是则查询条件排除自己id
        if (isExcludeSelf) {
            queryWrapper.ne(SysUser::getId, id);
        }
        int countByAccount = this.count(queryWrapper);
        //大于等于1个则表示重复
        if (countByAccount >= 1) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_REPEAT);
        }
    }

    /**
     * 获取系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserParam sysUserParam) {
        SysUser sysUser = this.getById(sysUserParam.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysUser;
    }

    @Override
    public List<Map<String,Object>> getUserByRoleCode(String roleCode) {
        return this.getBaseMapper().getUserByRoleCode(roleCode);
    }

    @Override
    public List<String> getUserNameByRoleCode(String roleCode) {
        return this.getBaseMapper().getUserNameByRoleCode(roleCode);
    }

    @Override
    public String getUserIdByName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getName,userName);
        queryWrapper.eq(SysUser::getStatus,CommonStatusEnum.ENABLE.getCode());
        queryWrapper.last("limit 1");
        SysUser sysUser = this.getOne(queryWrapper);
        return ObjectUtil.isNotEmpty(sysUser)?sysUser.getId().toString():null;
    }

    @Override
    public SysUser getUser(Long userId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId,userId);
        queryWrapper.eq(SysUser::getStatus,CommonStatusEnum.ENABLE.getCode());
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> getOrgUserList(Long orgId) {
        return this.getBaseMapper().getOrgUserList(orgId);
    }
    @Override
    public void downloadImportTpl(HttpServletResponse response) {
        Integer versions = DateTimeUtil.getNowStampUnix();
        String fileName = URLUtil.encode("【非密】软件配置管理系统人员信息导入模板") + versions + ".xls\"";
        String[] userHeaders = {"*账号", "*密级", "*姓名","*性别","*所属部门"};
        Workbook workbook = WorkbookUtil.createBook(false);
        ExcelTemplateUtil.createExcelTemplate(workbook, userHeaders, getUserDownData(), getUserDownRow(),
                "人员信息", versions.toString(), 1,null,false);
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(response.getOutputStream());
//            out.close();
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServiceException(SysFileInfoExceptionEnum.DOWNLOAD_FILE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object importUser(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, Object> projectBaseInfo = new HashMap<>();
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        List<String> existAccount = new ArrayList<>();
        List<SysUser> users = new ArrayList<>();
        for (int i = firstRowNum + 2; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            SysUser sysUser = new SysUser();
            //用户姓名
            Cell userAccountCell = row.getCell(0);
            if (ExcelTemplateUtil.checkCellValue(userAccountCell)) {
                String account = userAccountCell.getStringCellValue();
                if(existAccount.contains(account)){
                    return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:账号"+account+"重复");
                }
                if(userExists(account)){
                    return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:账号"+account+"已存在");
                }
                sysUser.setAccount(account);
                existAccount.add(account);
            } else {
                return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:用户账号信息不能为空");
            }
            //密级
            Cell uslCell = row.getCell(1);
            if (ExcelTemplateUtil.checkCellValue(uslCell)) {
                String usl = uslCell.getStringCellValue();
                String typeCode = checkDictCodeName(DictCodeConstant.SECRET_LEVEL_CODE, usl);
                if (ObjectUtil.isEmpty(typeCode)) {
                    return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:密级不存在");
                }
            } else {
                return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:密级信息不能为空");
            }

            //姓名
            Cell nameCell = row.getCell(2);
            if (ExcelTemplateUtil.checkCellValue(nameCell)) {
                String userName = nameCell.getStringCellValue();
                sysUser.setName(userName);
            } else {
                return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:姓名信息不能为空");
            }

            //性别
            Cell genderCell = row.getCell(3);
            if (ExcelTemplateUtil.checkCellValue(genderCell)) {
                String gender = genderCell.getStringCellValue();
                sysUser.setSex("男".equals(gender)?1:2);
            } else {
                return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:性别信息不能为空");
            }

            //性别
            Cell orgCell = row.getCell(4);
            if (ExcelTemplateUtil.checkCellValue(orgCell)) {
                String org = orgCell.getStringCellValue();
                SysOrg sysOrg = orgService.getOrgByNameAndCode(org);
                if (ObjectUtil.isEmpty(sysOrg)) {
                    return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:部门信息不存在");
                }
                sysUser.setTmpOrgId(sysOrg.getId());
                sysUser.setTmpOrgName(sysOrg.getName());
            } else {
                return ErrorResponseData.error("导入失败，行号："+(i+1)+",错误信息:部门信息不能为空");
            }
            sysUser.setAdminType(AdminTypeEnum.NONE.getCode());
            //没有密码则设置默认密码
            String password = EncryptUtil.decrypt(ConstantContextHolder.getDefaultPassWord());
            //设置密码为Md5加密后的密码
            sysUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            users.add(sysUser);
        }
        if(users.size()>0){
            for (SysUser user : users) {
                this.saveOrUpdate(user);
                SysEmp sysEmp = new SysEmp();
                sysEmp.setId(user.getId());
                sysEmp.setOrgId(user.getTmpOrgId());
                sysEmp.setOrgName(user.getTmpOrgName());
                sysEmpService.saveOrUpdate(sysEmp);
            }
        }else{
            return ErrorResponseData.error("导入失败，未检测到有效的用户数据");
        }
        return SuccessResponseData.success("成功导入"+users.size()+"条数据");
    }
    public String checkDictCodeName(String dictType, String dictCodeName) {
        return sysDictDataService.getDictTypeCodeByName(dictType, dictCodeName);
    }

    /**
     * 判断用户是否存在
     * @param account
     * @return
     */
    public boolean userExists(String account){
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getStatus, CommonStatusEnum.ENABLE.getCode());
        int count = this.count(queryWrapper);
        return count>0;
    }

    public List<String[]> getUserDownData(){
        List<String[]> downData = new ArrayList();
        //密级
        List<String> bslNameList = sysDictDataService.getDictDataNameByCode(DictCodeConstant.SECRET_LEVEL_CODE);
        String[] bslArr = bslNameList.toArray(new String[0]);
        downData.add(bslArr);

        String[] genderArr = {"男","女"};
        downData.add(genderArr);
        //部门名称
        List<String> orgNamesList = orgService.getOrgCodeNames();
        String[] orgArr = orgNamesList.toArray(new String[0]);
        downData.add(orgArr);
        return downData;
    }

    public String[] getUserDownRow(){
        return new String[]{"1","3","4"};
    }

    @Override
    public Long getUserByCountAndCreate(String account, String userName) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getAccount,account);
        query.last("limit 1");
        SysUser sysUser = this.getOne(query);
        if (ObjectUtil.isEmpty(sysUser)) {
            sysUser = new SysUser();
            sysUser.setName(userName);
            SysUserFactory.fillAddCommonUserInfo(sysUser);
            sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword(), BCrypt.gensalt()));
            this.save(sysUser);
            Long sysUserId = sysUser.getId();
            //增加员工信息
            SysOrg sysOrg = orgService.getById(LoginContextHolder.me().getSysLoginUser().getLoginEmpInfo().getOrgId());
            SysEmpParam sysEmpParam = new SysEmpParam();
            sysEmpParam.setOrgId(sysOrg.getId());
            sysEmpParam.setOrgName(sysOrg.getName());
            sysEmpParam.setId(sysUserId);
            sysEmpService.addOrUpdate(sysEmpParam);
        }
        return sysUser.getId();
    }

    @Override
    public boolean checkUserExist(String value, Long id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,value);
        queryWrapper.and(q -> q.eq(SysUser::getStatus,"0").or().eq(SysUser::getStatus,"1"));
        queryWrapper.last("limit 1");
        SysUser user = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(user)) {
            return false;
        }else {
            if(ObjectUtil.isNotEmpty(id)){
                return !user.getId().equals(id);
            }else{
                return true;
            }
        }
    }

    @Override
    public void deleteById(Long userId,Long orgId,boolean isSuperAdmin) {
        SysUser sysUser = this.getById(userId);
        SysEmp emp = sysEmpService.getById(userId);
        if(ObjectUtil.isNotEmpty(emp)&&!orgId.equals(emp.getOrgId())&&!isSuperAdmin){
            throw new ServiceException(500,"权限错误");
        }
        sysUser.setStatus(2);
        this.saveOrUpdate(sysUser);
    }

    @Override
    public void setPwd(Long userId, String password) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNotEmpty(sysUser)) {
            sysUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            this.saveOrUpdate(sysUser);
        }
    }
}
