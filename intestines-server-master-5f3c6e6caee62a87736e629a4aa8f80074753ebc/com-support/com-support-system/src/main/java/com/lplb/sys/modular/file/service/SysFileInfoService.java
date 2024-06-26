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
package com.lplb.sys.modular.file.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.core.pojo.page.PageResult;
import com.lplb.sys.modular.file.entity.SysFileInfo;
import com.lplb.sys.modular.file.param.SysFileInfoParam;
import com.lplb.sys.modular.file.result.SysFileInfoResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 文件信息表 服务类
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    /**
     * 分页查询文件信息表
     *
     * @param sysFileInfoParam 查询参数
     * @return 查询分页结果
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    PageResult<SysFileInfo> page(SysFileInfoParam sysFileInfoParam);

    /**
     * 查询所有文件信息表
     *
     * @param sysFileInfoParam 查询参数
     * @return 文件信息列表
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    List<SysFileInfo> list(SysFileInfoParam sysFileInfoParam);

    /**
     * 添加文件信息表
     *
     * @param sysFileInfoParam 添加参数
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    void add(SysFileInfoParam sysFileInfoParam);

    /**
     * 删除文件信息表
     *
     * @param sysFileInfoParam 删除参数
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    void delete(SysFileInfoParam sysFileInfoParam);

    /**
     * 编辑文件信息表
     *
     * @param sysFileInfoParam 编辑参数
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    void edit(SysFileInfoParam sysFileInfoParam);

    /**
     * 查看详情文件信息表
     *
     * @param sysFileInfoParam 查看参数
     * @return 文件信息
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    SysFileInfo detail(SysFileInfoParam sysFileInfoParam);

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file 要上传的文件
     * @return 文件id
     * @author stylefeng
     * @date 2020/6/9 21:21
     */
    Long uploadFile(MultipartFile file);

    Long uploadFile(File file);

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件id
     * @return 文件信息结果集
     * @author stylefeng
     * @date 2020/6/9 21:56
     */
    SysFileInfoResult getFileInfoResult(Long fileId, boolean returnBytes);

    /**
     * 判断文件是否存在
     *
     * @param field 文件id
     * @author xuyuxiang
     * @date 2020/6/28 15:55
     */
    void assertFile(Long field);

    /**
     * 文件预览
     *
     * @param sysFileInfoParam 文件预览参数
     * @param response         响应结果
     * @author xuyuxiang
     * @date 2020/7/7 11:23
     */
    void preview(SysFileInfoParam sysFileInfoParam, HttpServletResponse response);

    /**
     * 文件下载
     *
     * @param sysFileInfoParam 文件下载参数
     * @param response         响应结果
     * @author xuyuxiang
     * @date 2020/7/7 12:09
     */
    void download(SysFileInfoParam sysFileInfoParam, HttpServletResponse response);

    /**
     * 删除文件
     * @param fileArr
     */
    void deleteByFileIds(JSONArray fileArr);
}
