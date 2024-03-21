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
package com.lplb.sys.modular.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.core.pojo.node.AntdBaseTreeNode;
import com.lplb.core.pojo.page.PageResult;
import com.lplb.sys.modular.org.entity.SysOrg;
import com.lplb.sys.modular.org.param.SysOrgParam;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 系统组织机构service接口
 *
 * @author xuyuxiang
 * @date 2020/3/13 16:02
 */
public interface SysOrgService extends IService<SysOrg> {

    /**
     * 查询系统机构
     *
     * @param sysOrgParam 查询参数
     * @return 查询分页结果
     * @author yubaoshan
     * @date 2020/5/11 15:49
     */
    PageResult<Map<String,Object>> page(SysOrgParam sysOrgParam);

    /**
     * 系统组织机构列表
     *
     * @param sysOrgParam 查询参数
     * @return 组织机构列表
     * @author xuyuxiang
     * @date 2020/3/26 10:19
     */
    List<SysOrg> list(SysOrgParam sysOrgParam);

    /**
     * 添加系统组织机构
     *
     * @param sysOrgParam 添加参数
     * @author xuyuxiang
     * @date 2020/3/25 14:57
     */
    void add(SysOrgParam sysOrgParam);

    /**
     * 删除系统组织机构
     *
     * @param sysOrgParam 删除参数
     * @author xuyuxiang
     * @date 2020/3/25 14:57
     */
    void delete(SysOrgParam sysOrgParam);

    /**
     * 编辑系统组织机构
     *
     * @param sysOrgParam 编辑参数
     * @author xuyuxiang
     * @date 2020/3/25 14:58
     */
    void edit(SysOrgParam sysOrgParam);

    /**
     * 查看系统组织机构
     *
     * @param sysOrgParam 查看参数
     * @return 组织机构
     * @author xuyuxiang
     * @date 2020/3/26 9:50
     */
    SysOrg detail(SysOrgParam sysOrgParam);

    /**
     * 获取机构
     * @param orgId
     * @return
     */
    SysOrg getOrg(Long orgId);

    /**
     * 获取系统组织机构树
     *
     * @param sysOrgParam 查询参数
     * @return 系统组织机构树
     * @author xuyuxiang yubaoshan
     * @date 2020/3/26 11:56
     */
    List<AntdBaseTreeNode> tree(SysOrgParam sysOrgParam);

    /**
     * 根据数据范围类型获取当前登录用户的数据范围id集合
     *
     * @param dataScopeType 数据范围类型（1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据）
     * @param orgId         组织机构id
     * @return 数据范围id集合
     * @author xuyuxiang
     * @date 2020/4/5 18:29
     */
    List<Long> getDataScopeListByDataScopeType(Integer dataScopeType, Long orgId);

    /**
     * 获取所有的部门名称
     * @return
     */
    List<String> getOrgNames();
    List<String> getOrgCodeNames();

    /**
     * 根据机构部门名称获取机构部门
     * @param orgName
     */
    String getOrgByName(String orgName);

    SysOrg getOrgByNameAndCode(String str);

    void downloadImportTpl(HttpServletResponse response);

    Object importOrg(Workbook workbook);

    /**
     * 下拉选择数据
     * @param orgId
     * @return
     */
    List<Map<String,Object>> selectList(Long orgId);

    /**
     * 获取入驻机构列表
     * @return
     */
    List<SysOrg> getOrgList();
}
