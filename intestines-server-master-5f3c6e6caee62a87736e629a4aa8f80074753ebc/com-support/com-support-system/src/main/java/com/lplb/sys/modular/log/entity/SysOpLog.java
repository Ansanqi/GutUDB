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
package com.lplb.sys.modular.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 系统操作日志表
 *
 * @author xuyuxiang
 * @date 2020/3/11 11:56
 */
@Data
@TableName("sys_op_log")
public class SysOpLog {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 操作类型（见LogAnnotionOpTypeEnum）
     */
    @TableField("op_type")
    private Integer opType;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    @TableField("success")
    private String success;

    /**
     * 具体消息
     */
    @TableField("message")
    private String message;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 地址
     */
    @TableField("location")
    private String location;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 请求地址
     */
    @TableField("url")
    private String url;

    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 请求方式（GET POST PUT DELETE)
     */
    @TableField("req_method")
    private String reqMethod;

    /**
     * 请求参数
     */
    @TableField("param")
    private String param;

    /**
     * 返回结果
     */
    @TableField("result")
    private String result;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("op_time")
    private Date opTime;

    /**
     * 操作人
     */
    @TableField("account")
    private String account;
}
