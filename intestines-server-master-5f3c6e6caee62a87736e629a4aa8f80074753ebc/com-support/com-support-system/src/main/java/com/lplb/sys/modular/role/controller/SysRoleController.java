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
package com.lplb.sys.modular.role.controller;

import cn.hutool.json.JSONArray;
import com.lplb.core.annotion.BusinessLog;
import com.lplb.core.enums.LogAnnotionOpTypeEnum;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.modular.menu.service.SysMenuService;
import com.lplb.sys.modular.role.param.SysRoleParam;
import com.lplb.sys.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 *
 * @author xuyuxiang
 * @date 2020/3/20 19:42
 */
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:45
     */
    @GetMapping("/system/role/page")
//    @BusinessLog(title = "系统角色_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.page(sysRoleParam));
    }


    /**
     * 查询系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:45
     */
    @GetMapping("/sysRole/list/all")
//    @BusinessLog(title = "系统角色_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.listRoles(sysRoleParam));
    }

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/system/role")
    public Object sysRoleList(){
        return new SuccessResponseData(sysRoleService.roleList());
    }

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @author xuyuxiang
     * @date 2020/4/5 16:45
     */
    @GetMapping("/sysRole/dropDown")
//    @BusinessLog(title = "系统角色_下拉", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dropDown() {
        return new SuccessResponseData(sysRoleService.dropDown());
    }

    /**
     * 添加系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:45
     */
    @PostMapping("/system/role")
    @BusinessLog(title = "系统角色_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SysRoleParam sysRoleParam) {
        sysRoleService.add(sysRoleParam);
        return new SuccessResponseData();
    }
    /**
     * 删除系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:45
     */
    @DeleteMapping("/system/role/{roleId}")
    @BusinessLog(title = "系统角色_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@PathVariable("roleId") Long roleId) {
        sysRoleService.delete(roleId);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @PutMapping("/system/role")
    @BusinessLog(title = "系统角色_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody SysRoleParam sysRoleParam) {
        sysRoleService.edit(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @GetMapping("/sysRole/detail")
//    @BusinessLog(title = "系统角色_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.detail(sysRoleParam));
    }

    /**
     * 授权菜单
     *
     * @author xuyuxiang
     * @date 2020/3/28 16:05
     */
    @PostMapping("/sysRole/grantMenu")
    @BusinessLog(title = "系统角色_授权菜单", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantMenu(@RequestBody @Validated(SysRoleParam.grantMenu.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantMenu(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author xuyuxiang
     * @date 2020/3/28 16:05
     */
    @PostMapping("/sysRole/grantData")
    @BusinessLog(title = "系统角色_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysRoleParam.grantData.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantData(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有菜单
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @GetMapping("/sysRole/ownMenu")
//    @BusinessLog(title = "系统角色_拥有菜单", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownMenu(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownMenu(sysRoleParam));
    }

    /**
     * 拥有数据
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @GetMapping("/sysRole/ownData")
//    @BusinessLog(title = "系统角色_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownData(sysRoleParam));
    }

    /**
     * 获取用于分配的菜单
     * @param roleId
     * @return
     */
    @GetMapping("/system/role-menu/{roleId}")
    public Object getRoleMenu(@PathVariable("roleId") Long roleId){
        return SuccessResponseData.success(sysMenuService.getRoleMenu(roleId));
    }

    /**
     * 保存角色菜单
     * @param roleId
     * @param menus
     * @return
     */
    @PutMapping("/system/role-menu/{roleId}")
    public Object saveRoleMenu(@PathVariable("roleId") Long roleId,@RequestBody JSONArray menus){
        sysMenuService.saveRoleMenu(roleId,menus);
        return SuccessResponseData.success();
    }

}
