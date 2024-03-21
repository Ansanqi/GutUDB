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
package com.lplb.sys.modular.consts.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lplb.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 参数配置
 * </p>
 *
 * @author stylefeng
 * @date 2019/6/20 13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID,value = "ID")
    private Long id;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 属性值
     */
    @TableField("VALUE")
    private String value;

    /**
     * 是否是系统参数（Y-是，N-否）
     */
    @TableField("SYS_FLAG")
    private String sysFlag;

    /**
     * 备注
     */
    @TableField(value = "REMARK",insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @TableField("GROUP_CODE")
    private String groupCode;

    /**
     * 是否显示 0=不显示 1=显示
     */
    @TableField("is_show")
    private String isShow;

}