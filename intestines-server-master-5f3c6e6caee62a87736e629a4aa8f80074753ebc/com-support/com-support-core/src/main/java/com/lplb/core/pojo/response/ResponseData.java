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
package com.lplb.core.pojo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应结果数据
 *
 * @author xuyuxiang
 * @date 2020/3/30 15:04
 */
@Data
@ApiModel(value = "接口统一返回对象")
public class ResponseData<T> implements Serializable {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 0;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * 请求是否成功
     */
    @ApiModelProperty(value = "请求是否成功")
    private Boolean success;

    /**
     * 响应状态码
     */

    @ApiModelProperty(value = "响应状态码")
    private Integer code;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息")
    private String message;

    /**
     * 响应对象
     */
    @ApiModelProperty(value = "响应对象")
    private T data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(Boolean success, Integer code, String message, T data, String bsl) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponseData<T> success() {
        return new SuccessResponseData();
    }

    public static <T> SuccessResponseData<T> successMsg(String msg) {
        return new SuccessResponseData(0, msg, null);
    }

    public static <T> SuccessResponseData<T> success(T t) {
        return new SuccessResponseData(t);
    }

    public static <T> SuccessResponseData<T> success(Integer code, String message, T t) {
        return new SuccessResponseData(code, message, t);
    }

    public static <T> ErrorResponseData<T> error(String message) {
        return new ErrorResponseData(message);
    }

    public static <T> ErrorResponseData<T> error(Integer code, String message) {
        return new ErrorResponseData(code, message);
    }

    public static <T> ErrorResponseData<T> error(Integer code, String message, T t) {
        return new ErrorResponseData(code, message, t);
    }
}
