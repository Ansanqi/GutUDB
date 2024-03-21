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
package com.lplb.sys.modular.org.controller;

import com.lplb.core.annotion.BusinessLog;
import com.lplb.core.annotion.DataScope;
import com.lplb.core.annotion.Permission;
import com.lplb.core.enums.LogAnnotionOpTypeEnum;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.modular.auth.context.LoginContextSpringSecurityImpl;
import com.lplb.sys.modular.org.param.SysOrgParam;
import com.lplb.sys.modular.org.service.SysOrgRelationService;
import com.lplb.sys.modular.org.service.SysOrgService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统组织机构控制器
 *
 * @author xuyuxiang
 * @date 2020/3/20 19:47
 */
@RestController
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private LoginContextSpringSecurityImpl loginContext;

    @Resource
    private SysOrgRelationService sysOrgRelationService;

    /**
     * 查询系统机构
     *
     * @author xuyuxiang
     * @date 2020/5/11 15:49
     */
    @DataScope
    @GetMapping("/sysOrg/page")
    public ResponseData page(SysOrgParam sysOrgParam) {
        System.out.println(loginContext.getSysLoginUserOrgId());
        return new SuccessResponseData(sysOrgService.page(sysOrgParam));
    }

    /**
     * 系统组织机构列表
     *
     * @author xuyuxiang
     * @date 2020/3/26 10:20
     */
    @DataScope
    @GetMapping("/system/organization")
    public ResponseData list(SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.list(sysOrgParam));
    }


    /**
     * 下载导入模板
     */
    @GetMapping("/sysOrg/template/download")
    public void downloadImportTpl(HttpServletResponse response){
        sysOrgService.downloadImportTpl(response);
    }


    /**
     * 导入用户数据
     */
    @PostMapping("/sysOrg/import")
    public Object importUserData(@RequestPart("file") MultipartFile file) throws Exception{
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        return sysOrgService.importOrg(workbook);
    }

    /**
     * 添加系统组织机构
     *
     * @author xuyuxiang
     * @date 2020/3/25 14:44
     */
    @DataScope
    @PostMapping("/system/organization")
    @BusinessLog(title = "系统组织机构_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SysOrgParam sysOrgParam) {
        sysOrgService.add(sysOrgParam);
        return new SuccessResponseData();
    }

    /**
     * 机构下拉选择数据
     * @param orgId
     * @return
     */
    @GetMapping("/system/organization/list")
    public ResponseData selectList(Long orgId) {

        return new SuccessResponseData(sysOrgService.selectList(orgId));
    }

    /**
     * 删除系统组织机构
     *
     * @author xuyuxiang
     * @date 2020/3/25 14:54
     */
    @DataScope
    @DeleteMapping("/system/organization/{id}")
    @BusinessLog(title = "系统组织机构_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@PathVariable("id") Long id) {
        SysOrgParam sysOrgParam = new SysOrgParam();
        sysOrgParam.setId(id);
        sysOrgService.delete(sysOrgParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统组织机构
     *
     * @author xuyuxiang
     * @date 2020/3/25 14:54
     */
    @DataScope
    @PutMapping("/system/organization")
    @BusinessLog(title = "系统组织机构_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody SysOrgParam sysOrgParam) {
        sysOrgService.edit(sysOrgParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统组织机构
     *
     * @author xuyuxiang
     * @date 2020/3/26 9:49
     */
    @Permission
    @GetMapping("/sysOrg/detail")
//    @BusinessLog(title = "系统组织机构_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysOrgParam.detail.class) SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.detail(sysOrgParam));
    }

    /**
     * 获取组织机构树
     *
     * @author xuyuxiang
     * @date 2020/3/26 11:55
     */
//    @Permission
    @DataScope
    @GetMapping("/sysOrg/tree")
//    @BusinessLog(title = "系统组织机构_树", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData tree(SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.tree(sysOrgParam));
    }

    /**
     * 获取机构关联信息
     * @return
     */
    @GetMapping("/system/organization/relation/page")
    public Object getSysOrgRelation(Long orgId){
        return sysOrgRelationService.getOrgRelationPage(orgId);
    }

    /**
     * 保存
     * @return
     */
    @PostMapping("/system/organization/relation")
    public Object saveOrgRelation(Long orgId,Long salveId){
        return sysOrgRelationService.saveOrgRelation(orgId,salveId);
    }

    /**
     * 删除机构关联信息
     * @return
     */
    @DeleteMapping("/system/organization/relation")
    public Object deleteOrgRelation(Long relationId){
        return sysOrgRelationService.deleteOrgRelation(relationId);
    }
}
