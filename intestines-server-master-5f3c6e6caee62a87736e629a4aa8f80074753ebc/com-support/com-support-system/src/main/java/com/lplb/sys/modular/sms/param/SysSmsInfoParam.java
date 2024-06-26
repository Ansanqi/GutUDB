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
package com.lplb.sys.modular.sms.param;


import com.lplb.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统短信表
 *
 * @author stylefeng
 * @date 2018/7/5 13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsInfoParam extends BaseParam {

    /**
     * 主键
     */
    private Long id;

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 短信验证码
     */
    private String validateCode;

    /**
     * 短信模板ID
     */
    private String templateCode;

    /**
     * 回执id，可根据该id查询具体的发送状态
     */
    private String bizId;

    /**
     * 发送状态（字典 0 未发送，1 发送成功，2 发送失败，3 失效）
     */
    private Integer status;

    /**
     * 来源（字典 1 app， 2 pc， 3 其他）
     */
    private Integer source;

    /**
     * 失效时间
     */
    private Date invalidTime;
}
