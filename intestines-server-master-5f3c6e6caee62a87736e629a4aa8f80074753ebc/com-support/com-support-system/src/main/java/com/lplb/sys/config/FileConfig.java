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
package com.lplb.sys.config;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.file.FileOperator;
import cn.stylefeng.roses.file.modular.local.LocalFileOperator;
import cn.stylefeng.roses.file.modular.local.prop.LocalFileProperties;
import com.lplb.core.context.constant.ConstantContextHolder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * 文件存储的配置
 * <p>
 * 默认激活本地文件存储
 *
 * @author stylefeng
 * @date 2020/6/6 22:27
 */
@Configuration
public class FileConfig {

    /**
     * 默认文件存储的位置
     */
    public static final String DEFAULT_BUCKET = "defaultbucket";

    /**
     * 配置库备份文件存储的位置
     */
    public static final String BACKUPS_BUCKET = "databackups";

    public static final String ZIP_ENC_KEY = "fi4usi13rj8wdss3kl";
    /**
     * 本地文件操作客户端
     *
     * @author stylefeng
     * @date 2020/6/9 21:39
     */
    @Bean
    public FileOperator fileOperator() {
        LocalFileProperties localFileProperties = new LocalFileProperties();
        String fileUploadPathForWindows = ConstantContextHolder.getDefaultFileUploadPathForWindows();
        if (ObjectUtil.isNotEmpty(fileUploadPathForWindows)) {
            localFileProperties.setLocalFileSavePathWin(fileUploadPathForWindows);
        }

        String fileUploadPathForLinux = ConstantContextHolder.getDefaultFileUploadPathForLinux();
        if (ObjectUtil.isNotEmpty(fileUploadPathForLinux)) {
            localFileProperties.setLocalFileSavePathLinux(fileUploadPathForLinux);
        }
        return new LocalFileOperator(localFileProperties);
    }

    /**
     * 文件上传限制
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(20480, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(20480, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
