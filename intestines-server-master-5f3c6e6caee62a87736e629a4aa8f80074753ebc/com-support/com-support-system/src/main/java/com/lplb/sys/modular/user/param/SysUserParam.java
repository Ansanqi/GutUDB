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
package com.lplb.sys.modular.user.param;

import com.lplb.core.pojo.base.param.BaseParam;
import com.lplb.sys.modular.emp.param.SysEmpParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 系统用户参数
 *
 * @author xuyuxiang
 * @date 2020/3/23 9:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserParam extends BaseParam {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数",
            groups = {edit.class, delete.class, detail.class, start.class,
                    stop.class, grantRole.class, grantData.class, updateInfo.class, resetPwd.class, changeStatus.class, updateAvatar.class})
    private Long id;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空，请检查account参数", groups = {add.class, edit.class})
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空，请检查password参数", groups = {updatePwd.class})
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空，请检查newPassword参数", groups = {updatePwd.class})
    private String newPassword;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空，请检查name参数", groups = {add.class, edit.class})
    private String name;

    /**
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别(字典 1男 2女)
     */
    @NotNull(message = "性别不能为空，请检查sex参数", groups = {updateInfo.class})
    @Min(value = 1, message = "性别格式错误，请检查sex参数", groups = {updateInfo.class})
    @Max(value = 2, message = "性别格式错误，请检查sex参数", groups = {updateInfo.class})
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String tel;

    /**
     * 授权角色
     */
    @NotNull(message = "授权角色不能为空，请检查grantRoleIdList参数", groups = {grantRole.class})
    private List<Long> roleIds;

    /**
     * 授权数据
     */
    @NotNull(message = "授权数据不能为空，请检查grantOrgIdList参数", groups = {grantData.class})
    private List<Long> grantOrgIdList;

    /*==============员工相关信息==========*/

    private SysEmpParam sysEmpParam;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @NotNull(message = "状态不能为空",groups = changeStatus.class)
    private Integer status;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 是机构管理员
     */
    private boolean isOrgAdmin;

    /**
     * 所属部门
     */
    private String deptIds;
}
