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
package com.lplb.sys.modular.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.lplb.core.context.constant.ConstantContextHolder;
import com.lplb.core.context.login.LoginContextHolder;
import com.lplb.core.pojo.response.ErrorResponseData;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.core.cache.CaptchaCache;
import com.lplb.sys.modular.auth.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author xuyuxiang
 * @date 2020/3/11 12:20
 */
@RestController
public class SysLoginController {

    @Resource
    private AuthService authService;

    @Resource
    private CaptchaCache captchaCache;
    
    /**
     * 账号密码登录
     *
     * @author xuyuxiang
     * @date 2020/3/11 15:52
     */
    @PostMapping("/login")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("username");
        String password = dict.getStr("password");
        String tenantCode = dict.getStr("tenantCode");
        String code = dict.getStr("code");
        String uuid = dict.getStr("uuid");
        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (ConstantContextHolder.getTenantOpenFlag()) {
            authService.cacheTenantInfo(tenantCode);
        }
        Map<String, Object> captchaMap = captchaCache.get(uuid);
        if (ObjectUtil.isEmpty(code)) {
            return ErrorResponseData.error("请输入验证码");
        }
        if (ObjectUtil.isEmpty(captchaMap)) {
            return ErrorResponseData.error("验证码失效");
        }else{
            String codeCache = captchaMap.getOrDefault("code","").toString();
            if(!codeCache.equals(code)){
                captchaCache.remove(uuid);
                return ErrorResponseData.error("验证码错误");
            }
        }

        Map<String,Object> userInfo = authService.login(account, password);
        captchaCache.remove(uuid);
        return new SuccessResponseData(0,"登录成功",userInfo);
    }


    /**
     * 退出登录
     *
     * @author xuyuxiang
     * @date 2020/3/16 15:02
     */
    @GetMapping("/logout")
    public Object logout() {
        authService.logout();
        return SuccessResponseData.success();
    }

    /**
     * 获取当前登录用户信息
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/23 17:57
     */
    @GetMapping("/getLoginUser")
    public ResponseData getLoginUser() {
        return new SuccessResponseData(LoginContextHolder.me().getSysLoginUserUpToDate());
    }

    @GetMapping("/captcha")
    public Object getCaptcha(){
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 38);
        String random = RandomUtil.randomString(4);
        Image image = circleCaptcha.createImage(random);
        Map<String,Object> result = new HashMap<>();
        String uuid = IdUtil.simpleUUID();
        result.put("base64", "data:image/png;base64,"+ImgUtil.toBase64(image,ImgUtil.IMAGE_TYPE_PNG));
        result.put("uuid",uuid);
        Map<String,Object> cacheMap = new HashMap<>();
        cacheMap.put("code",random);
        captchaCache.put(uuid,cacheMap, 60L);
        return SuccessResponseData.success(result);
    }
}
