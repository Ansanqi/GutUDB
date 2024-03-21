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
package com.lplb.sys.modular.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.factory.PageFactory;
import com.lplb.core.pojo.page.PageResult;
import com.lplb.sys.modular.log.entity.SysVisLog;
import com.lplb.sys.modular.log.mapper.SysVisLogMapper;
import com.lplb.sys.modular.log.param.SysVisLogParam;
import com.lplb.sys.modular.log.service.SysVisLogService;
import com.lplb.sys.modular.user.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统访问日志service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/3/12 14:23
 */
@Service
public class SysVisLogServiceImpl extends ServiceImpl<SysVisLogMapper, SysVisLog> implements SysVisLogService {

    @Resource
    SysUserRoleService sysUserRoleService;

    @Override
    public PageResult<Map<String,Object>> page(SysVisLogParam sysVisLogParam) {
        List<String> userList = new ArrayList<>();
        Page<Map<String,Object>> result = this.getBaseMapper().getVisLogPage(PageFactory.defaultPage(),
                sysVisLogParam.getName(), sysVisLogParam.getAccount(), sysVisLogParam.getVisType(),sysVisLogParam.getSuccess(),userList,sysVisLogParam.getStartTime(),sysVisLogParam.getEndTime());
//        LambdaQueryWrapper<SysVisLog> queryWrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtil.isNotNull(sysVisLogParam)) {
//            //根据名称模糊查询
//            if (ObjectUtil.isNotEmpty(sysVisLogParam.getName())) {
//                queryWrapper.like(SysVisLog::getName, sysVisLogParam.getName());
//            }
//            //跟据访问类型（字典 1登入 2登出）查询
//            if (ObjectUtil.isNotEmpty(sysVisLogParam.getVisType())) {
//                queryWrapper.eq(SysVisLog::getVisType, sysVisLogParam.getVisType());
//            }
//            //根据是否成功查询
//            if (ObjectUtil.isNotEmpty(sysVisLogParam.getSuccess())) {
//                queryWrapper.eq(SysVisLog::getSuccess, sysVisLogParam.getSuccess());
//            }
//        }
        return new PageResult<>(result);
    }

    @Override
    public void delete() {
        this.remove(new QueryWrapper<>());
    }
}
