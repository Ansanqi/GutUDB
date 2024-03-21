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
package com.lplb.sys.modular.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lplb.core.annotion.BusinessLog;
import com.lplb.core.annotion.DataScope;
import com.lplb.core.annotion.Permission;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.context.constant.ConstantContextHolder;
import com.lplb.core.enums.LogAnnotionOpTypeEnum;
import com.lplb.core.pojo.response.ErrorResponseData;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.core.util.DateTimeUtil;
import com.lplb.sys.modular.auth.context.LoginContextSpringSecurityImpl;
import com.lplb.sys.modular.user.entity.SysUser;
import com.lplb.sys.modular.user.param.SysUserParam;
import com.lplb.sys.modular.user.service.SysUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 系统用户控制器
 *
 * @author xuyuxiang
 * @date 2020/3/19 21:14
 */
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private LoginContextSpringSecurityImpl loginContext;

    /**
     * 查询系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/20 21:00
     */
    @DataScope
    @GetMapping("/system/user/page")
    public ResponseData page(String account,String name,Long organizationId) {
        if(ObjectUtil.isEmpty(organizationId)){
            organizationId = loginContext.getSysLoginUserOrgId();
        }
//        boolean superAdmin = loginContext.isSuperAdmin();
//        if(ObjectUtil.isEmpty(organizationId)&&!superAdmin){
//            return ErrorResponseData.error("请选择机构再进行查询");
//        }
        return new SuccessResponseData(sysUserService.page(account,name,organizationId));
    }

    /**
     * 获取系统用户列表
     * @return
     */
//    @Permission
//    @DataScope
    @GetMapping("/sysUser/list")
    public Object getBusinessUserList(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.userList(sysUserParam));
    }

    @GetMapping("/sysUser/list/select")
    public Object getBusinessUserListForSelect(SysUserParam sysUserParam) {
        sysUserParam.setOrgAdmin(loginContext.isOrgAdmin());
        sysUserParam.setOrgId(loginContext.getSysLoginUserOrgId());
        return new SuccessResponseData(sysUserService.userSelectList(sysUserParam));
    }

    /**
     * 增加系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @PostMapping("/system/user")
    @BusinessLog(title = "系统用户_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysUserParam.add.class) SysUserParam sysUserParam) {
        if (ObjectUtil.isEmpty(sysUserParam.getOrgId())) {
            sysUserParam.setOrgId(loginContext.getSysLoginUserOrgId());
        }
        sysUserService.add(sysUserParam);
        return ResponseData.successMsg("用户添加成功");
    }

    /**
     * 下载导入模板
     */
    @GetMapping("/sysUser/template/download")
    public void downloadImportTpl(HttpServletResponse response){
        sysUserService.downloadImportTpl(response);
    }

    /**
     * 导入用户数据
     */
    @PostMapping("/sysUser/import")
    public Object importUserData(@RequestPart("file")  MultipartFile file) throws Exception{
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        return sysUserService.importUser(workbook);
    }

    /**
     * 删除系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @DataScope
    @DeleteMapping("/system/user/{userId}")
    @BusinessLog(title = "系统用户_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@PathVariable("userId") Long userId) {
        sysUserService.deleteById(userId,loginContext.getSysLoginUserOrgId(),loginContext.isSuperAdmin());
        return ResponseData.successMsg("用户删除成功");
    }

    /**
     * 编辑系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @PutMapping("/system/user")
    @BusinessLog(title = "系统用户_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysUserParam.edit.class) SysUserParam sysUserParam) {
        sysUserService.edit(sysUserParam);
        return ResponseData.successMsg("用户修改成功");
    }

    /**
     * 查看系统用户
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @Permission
    @GetMapping("/sysUser/detail")
//    @BusinessLog(title = "系统用户_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.detail(sysUserParam));
    }

    /**
     * 修改状态
     *
     * @author xuyuxiang
     * @date 2020/5/25 14:32
     */
    @Permission
    @PostMapping("/sysUser/changeStatus")
    @BusinessLog(title = "系统用户_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysUserParam.changeStatus.class) SysUserParam sysUserParam) {
        return sysUserService.changeStatus(sysUserParam);
    }

    /**
     * 授权角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 16:05
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/grantRole")
    @BusinessLog(title = "系统用户_授权角色", opType = LogAnnotionOpTypeEnum.GRANT)
    public Object grantRole(@RequestBody @Validated(SysUserParam.grantRole.class) SysUserParam sysUserParam) {
        return sysUserService.grantRole(sysUserParam);
    }

    /**
     * 授权数据
     *
     * @author xuyuxiang
     * @date 2020/3/28 16:05
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/grantData")
    @BusinessLog(title = "系统用户_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysUserParam.grantData.class) SysUserParam sysUserParam) {
        sysUserService.grantData(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 更新信息
     *
     * @author xuyuxiang
     * @date 2020/4/1 14:27
     */
    @PostMapping("/sysUser/updateInfo")
    @BusinessLog(title = "系统用户_更新信息", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateInfo(@RequestBody @Validated(SysUserParam.updateInfo.class) SysUserParam sysUserParam) {
        return sysUserService.updateInfo(sysUserParam);
    }

    /**
     * 修改密码
     *
     * @author xuyuxiang
     * @date 2020/4/1 14:42
     */
    @PutMapping("/sysUser/updatePwd")
    @BusinessLog(title = "系统用户_修改密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updatePwd(@RequestBody @Validated(SysUserParam.updatePwd.class) SysUserParam sysUserParam) {
        sysUserParam.setId(loginContext.getSysLoginUserId());
        sysUserService.updatePwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有角色
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysUser/ownRole")
//    @BusinessLog(title = "系统用户_拥有角色", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownRole(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownRole(sysUserParam));
    }

    /**
     * 拥有数据
     *
     * @author xuyuxiang
     * @date 2020/3/28 14:46
     */
    @Permission
    @GetMapping("/sysUser/ownData")
//    @BusinessLog(title = "系统用户_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownData(sysUserParam));
    }

    /**
     * 重置密码
     *
     * @author xuyuxiang
     * @date 2020/4/1 14:42
     */
    @PutMapping("/system/user/password")
    @BusinessLog(title = "系统用户_重置密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData resetPwd(@RequestBody @Validated(SysUserParam.resetPwd.class) SysUserParam sysUserParam) {
        sysUserService.resetPwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 平台管理员设置用户密码
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/system/admin/password")
    public Object adminSetUserPwd(Long userId,String password){
        if (!loginContext.isSuperAdmin()) {
            return ErrorResponseData.error(CommonConstant.TIP_NO_PERMISSION);
        }
        sysUserService.setPwd(userId,password);
        return SuccessResponseData.success();
    }

    /**
     * 修改头像
     *
     * @author xuyuxiang
     * @date 2020/6/28 15:19
     */
    @PostMapping("/sysUser/updateAvatar")
//    @BusinessLog(title = "系统用户_修改头像", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateAvatar(@RequestBody @Validated(SysUserParam.updateAvatar.class) SysUserParam sysUserParam) {
        sysUserService.updateAvatar(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 导出系统用户
     *
     * @author xuyuxiang
     * @date 2020/6/30 16:07
     */
    @Permission
    @GetMapping("/sysUser/export")
//    @BusinessLog(title = "系统用户_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysUserParam sysUserParam) {
        sysUserService.export(sysUserParam);
    }


    /**
     * 用户选择器
     *
     * @author xuyuxiang
     * @date 2020/7/3 13:17
     */
    @Permission
    @GetMapping("/sysUser/selector")
//    @BusinessLog(title = "系统用户_选择器", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selector(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.selector(sysUserParam));
    }
    /**
     * 检查用户密码是否需要进行更换
     * @return
     */
    @GetMapping("/sysUser/check/pwdTime")
    public Object checkUserPwdTime() {
        SysUser user = sysUserService.getById(loginContext.getSysLoginUserId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTimeUtil.getNow());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String indStatus = ConstantContextHolder.getSystemOrgIndependent();
        if ("false".equals(indStatus)) {
            if ("1".equals(user.getAdminType().toString())) {
                return SuccessResponseData.success(ObjectUtil.isEmpty(user.getPwdTime()) || user.getPwdTime().before(calendar.getTime()));
            }
        } else {//独立组织架构全体用户都需判断密码设置时间
            return SuccessResponseData.success(ObjectUtil.isEmpty(user.getPwdTime()) || user.getPwdTime().before(calendar.getTime()));
        }
        return SuccessResponseData.success(false);
    }


    /**
     * 检查用户密码是否需要进行更换
     * @return
     */
    @GetMapping("/sysUser/check/pwdStatus")
    public Object checkUserPwdStatus() {
        SysUser user = sysUserService.getById(loginContext.getSysLoginUserId());
        return SuccessResponseData.success("0".equals(user.getPwdStatus()));
    }

    /**
     * 获取机构组织用户
     * @return
     */
    @GetMapping("/sysUser/for/org")
    public Object getOrgUserList(@RequestParam Long orgId){
        List<Map<String,Object>> orgUserList = sysUserService.getOrgUserList(orgId);
        return SuccessResponseData.success(orgUserList);
    }

    /**
     * 获取系统组织架构部署方式
     * @return
     */
    @GetMapping("/system/user/existence")
    public Object userExistence(String value,Long id){
        boolean isExist = sysUserService.checkUserExist(value,id);
        if(isExist){
            return SuccessResponseData.success();
        }
        return null;
    }
}
