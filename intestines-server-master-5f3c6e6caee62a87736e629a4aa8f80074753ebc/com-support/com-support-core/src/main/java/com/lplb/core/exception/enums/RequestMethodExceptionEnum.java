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
package com.lplb.core.exception.enums;

import com.lplb.core.annotion.ExpEnumType;
import com.lplb.core.consts.ExpEnumConstant;
import com.lplb.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.lplb.core.factory.ExpEnumCodeFactory;

/**
 * 请求方法相关异常枚举
 *
 * @author xuyuxiang
 * @date 2020/3/11 15:33
 */
@ExpEnumType(module = ExpEnumConstant.GUNS_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.REQUEST_METHOD_EXCEPTION_ENUM)
public enum RequestMethodExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 不支持该请求方法，请求方法应为POST
     */
    REQUEST_METHOD_IS_POST(1, "不支持该请求方法，请求方法应为POST"),

    /**
     * 不支持该请求方法，请求方法应为GET
     */
    REQUEST_METHOD_IS_GET(2, "不支持该请求方法，请求方法应为GET");

    private final Integer code;

    private final String message;

    RequestMethodExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
