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
package com.lplb.sys.modular.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.sys.modular.role.entity.SysRole;
import com.lplb.sys.modular.user.entity.SysUserRole;
import com.lplb.sys.modular.user.param.SysUserParam;

import java.util.List;

/**
 * 系统用户角色service接口
 *
 * @author xuyuxiang
 * @date 2020/3/13 15:44
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @author xuyuxiang
     * @date 2020/3/20 22:29
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 授权角色
     *
     * @param sysUserParam 授权参数
     * @author xuyuxiang
     * @date 2020/3/28 16:57
     */
    void grantRole(SysUserParam sysUserParam);

    /**
     * 获取用户所有角色的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author xuyuxiang
     * @date 2020/4/5 17:31
     */
    List<Long> getUserRoleDataScopeIdList(Long userId, Long orgId);

    /**
     * 根据角色id删除对应的用户-角色表关联信息
     *
     * @param roleId 角色id
     * @author xuyuxiang
     * @date 2020/6/28 14:22
     */
    void deleteUserRoleListByRoleId(Long roleId);

    /**
     * 根据用户id删除对应的用户-角色表关联信息
     *
     * @param userId 用户id
     * @author xuyuxiang
     * @date 2020/6/28 14:52
     */
    void deleteUserRoleListByUserId(Long userId);

    /**
     * 获取用户角色名称
     * @param userId
     * @return
     */
    List<String> getUserRoleName(Long userId);

    /**
     * 获取用户橘色编码
     * @param userId
     * @return
     */
    List<String> getUserRoleCode(Long userId);

    /**
     * 根据角色编码获取角色
     * @param rolePccbCode
     */
    SysRole getRoleByCode(String roleCode);

    /**
     * 根据角色ID获取用户ID
     * @param roleId
     * @return
     */
    List<Long> getUserIdByRole(Long roleId);

    /**
     * 获取用户的角色信息
     * @param id
     * @return
     */
    List<SysRole> getUserRole(Long id);
}
