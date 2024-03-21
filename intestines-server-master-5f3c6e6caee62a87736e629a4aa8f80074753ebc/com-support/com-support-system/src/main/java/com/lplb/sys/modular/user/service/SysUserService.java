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

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.sys.modular.user.entity.SysUser;
import com.lplb.sys.modular.user.param.SysUserParam;
import com.lplb.sys.modular.user.result.SysUserResult;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 系统用户service接口
 *
 * @author xuyuxiang
 * @date 2020/3/11 17:49
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据账号获取用户
     *
     * @param account 账号
     * @return 用户
     * @author xuyuxiang
     * @date 2020/3/11 17:51
     */
    SysUser getUserByCount(String account);

    /**
     * 查询系统用户
     *
     * @param sysUserParam 查询参数
     * @return 查询分页结果
     * @author xuyuxiang
     * @date 2020/3/23 9:23
     */
    Object page(String account,String name,Long orgId);


    /**
     * 查询系统用户
     *
     * @param sysUserParam 查询参数
     * @return 查询分页结果
     * @author xuyuxiang
     * @date 2020/3/23 9:23
     */
    List<SysUserResult> userList(SysUserParam sysUserParam);

    /**
     * 用户下拉选择
     * @param sysUserParam
     * @return
     */
    List<SysUserResult> userSelectList(SysUserParam sysUserParam);

    /**
     * 根据用户账号模糊搜索系统用户列表
     *
     * @param sysUserParam 查询参数
     * @return 增强版hashMap 格式：[{"id:":123, "firstName":"张三"}]
     * @author xuyuxiang
     * @date 2020/4/14 9:21
     */
    List<Dict> list(SysUserParam sysUserParam);

    /**
     * 增加系统用户
     *
     * @param sysUserParam 添加参数
     * @author xuyuxiang
     * @date 2020/3/23 9:26
     */
    void add(SysUserParam sysUserParam);

    /**
     * 删除系统用户
     *
     * @param sysUserParam 删除参数
     * @author xuyuxiang
     * @date 2020/3/23 9:26
     */
    void delete(SysUserParam sysUserParam);

    /**
     * 编辑系统用户
     *
     * @param sysUserParam 编辑参数
     * @author xuyuxiang
     * @date 2020/3/23 9:26
     */
    void edit(SysUserParam sysUserParam);

    /**
     * 查看系统用户
     *
     * @param sysUserParam 查看参数
     * @return 用户结果集
     * @author xuyuxiang
     * @date 2020/3/26 9:52
     */
    SysUserResult detail(SysUserParam sysUserParam);

    /**
     * 修改状态
     *
     * @param sysUserParam 修改参数
     * @author xuyuxiang
     * @date 2020/5/25 14:34
     */
    ResponseData changeStatus(SysUserParam sysUserParam);

    /**
     * 授权角色
     *
     * @param sysUserParam 授权参数
     * @author xuyuxiang
     * @date 2020/3/28 16:54
     */
    Object grantRole(SysUserParam sysUserParam);

    /**
     * 授权数据
     *
     * @param sysUserParam 授权参数
     * @author xuyuxiang
     * @date 2020/3/28 16:54
     */
    void grantData(SysUserParam sysUserParam);

    /**
     * 更新信息
     *
     * @param sysUserParam 更新参数
     * @author xuyuxiang
     * @date 2020/4/1 14:43
     */
    ResponseData updateInfo(SysUserParam sysUserParam);

    /**
     * 修改密码
     *
     * @param sysUserParam 修改密码参数
     * @author xuyuxiang
     * @date 2020/4/1 14:44
     */
    void updatePwd(SysUserParam sysUserParam);

    /**
     * 获取用户的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author xuyuxiang
     * @date 2020/4/5 17:23
     */
    List<Long> getUserDataScopeIdList(Long userId, Long orgId);

    /**
     * 根据用户id获取姓名
     *
     * @param userId 用户id
     * @return 用户姓名
     * @author xuyuxiang
     * @date 2020/5/6 15:02
     */
    String getNameByUserId(Long userId);


    /**
     * 根据用户id获取姓名
     *
     * @param userId 用户id
     * @return 用户姓名
     * @author xuyuxiang
     * @date 2020/5/6 15:02
     */
    String getNameByUser(Long userId);

    /**
     * 获取多个用户的姓名，返回值姓名之间用逗号隔开
     * @param userIds
     * @return
     */
    String getNamesByUsers(String userIds);



    /**
     * 拥有角色
     *
     * @param sysUserParam 查询参数
     * @return 角色id集合
     * @author xuyuxiang
     * @date 2020/5/29 14:10
     */
    List<Long> ownRole(SysUserParam sysUserParam);

    /**
     * 拥有数据
     *
     * @param sysUserParam 查询参数
     * @return 数据范围id集合
     * @author xuyuxiang
     * @date 2020/5/29 14:10
     */
    List<Long> ownData(SysUserParam sysUserParam);

    /**
     * 重置密码
     *
     * @param sysUserParam 重置参数
     * @author xuyuxiang
     * @date 2020/5/29 14:57
     */
    void resetPwd(SysUserParam sysUserParam);

    /**
     * 修改头像
     *
     * @param sysUserParam 修改头像参数
     * @author xuyuxiang
     * @date 2020/6/28 15:21
     */
    void updateAvatar(SysUserParam sysUserParam);

    /**
     * 导出用户
     *
     * @param sysUserParam 导出参数
     * @author xuyuxiang
     * @date 2020/6/30 16:08
     */
    void export(SysUserParam sysUserParam);

    /**
     * 用户选择器
     *
     * @param sysUserParam 查询参数
     * @return 用户列表集合，格式[{"id":123,"name":"张三"},{"id":456,"name":"李四"}]
     * @author xuyuxiang
     * @date 2020/7/3 13:17
     */
    List<Dict> selector(SysUserParam sysUserParam);

    /**
     * 根据角色获取用户
     * @param project_ccb
     * @return
     */
    List<Map<String,Object>> getUserByRoleCode(String roleCode);

    /**
     * 根据角色获取用户名
     * @param roleCode
     * @return
     */
    List<String> getUserNameByRoleCode(String roleCode);

    /**
     * 根据用户名获取用户ID
     * @param userName
     * @return
     */
    String getUserIdByName(String userName);

    /**
     * 获取用户
     * @param userId
     * @return
     */
    SysUser getUser(Long userId);

    /**
     * 获取结构用户
     * @param orgId
     * @return
     */
    List<Map<String, Object>> getOrgUserList(Long orgId);

    /**
     * 下载导入模板
     * @param response
     */
    void downloadImportTpl(HttpServletResponse response);

    /**
     * 导入用户
     * @param file
     * @return
     */
    Object importUser(Workbook workbook);

    /**
     * 根据账号获取用户，没有则创建用户
     * @param account
     * @param userName
     * @return
     */
    Long getUserByCountAndCreate(String account, String userName);

    /**
     * 检查用户账号是否已存在
     * @param value
     * @return
     */
    boolean checkUserExist(String value,Long id);

    /**
     * 删除用户
     * @param userId
     */
    void deleteById(Long userId,Long orgId,boolean isSuperAdmin);

    /**
     * 管理员修改用户密码
     * @param userId
     * @param password
     */
    void setPwd(Long userId, String password);
}
