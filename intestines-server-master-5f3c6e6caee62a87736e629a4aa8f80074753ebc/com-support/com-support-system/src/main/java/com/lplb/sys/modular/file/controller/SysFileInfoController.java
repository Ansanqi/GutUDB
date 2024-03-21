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
package com.lplb.sys.modular.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lplb.core.annotion.BusinessLog;
import com.lplb.core.annotion.Permission;
import com.lplb.core.enums.LogAnnotionOpTypeEnum;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.modular.file.entity.SysFileInfo;
import com.lplb.sys.modular.file.param.SysFileInfoParam;
import com.lplb.sys.modular.file.service.SysFileInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * 文件信息表 控制器
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@RestController
public class SysFileInfoController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * 上传文件
     *
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    @PostMapping(value = "/sysFileInfo/upload",headers = "content-type=multipart/form-data")
    @BusinessLog(title = "文件信息表_上传文件", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData upload(@RequestPart("file") MultipartFile file) {
        Long fileId = sysFileInfoService.uploadFile(file);
        return new SuccessResponseData(fileId);
    }

    /**
     *获取文件预上传ID
     * @return
     */
    @GetMapping("/sysFileInfo/fileId")
    public Object getFileIdForUpload(SysFileInfo fileInfo,String modularName){
//        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysFileInfo::getFileMD5,fileInfo.getFileMD5());
//        int count = sysFileInfoService.count(queryWrapper);
//        if (count>0) {
//            return ResponseData.error(201,"系统中已存在系统文件");
//        }
        Long fileId = IdWorker.getId();
        fileInfo.setFileBucket("adgu-hd");
        String cloudPath = getFileCloudPath(modularName);
        fileInfo.setFilePath(cloudPath+fileId+"."+fileInfo.getFileSuffix());
        fileInfo.setFileObjectName(fileId+"."+fileInfo.getFileSuffix());
        if (ObjectUtil.isNotEmpty(fileInfo.getFileSizeB())) {
            String fileSizeInfo = FileUtil.readableFileSize(fileInfo.getFileSizeB());
            fileInfo.setFileSizeInfo(fileSizeInfo);
        }
        sysFileInfoService.saveOrUpdate(fileInfo);
        return SuccessResponseData.success(fileInfo);
    }

    /**
     * 下载文件
     *
     * @author stylefeng, xuyuxiang
     * @date 2020/6/9 21:53
     */
    @GetMapping("/sysFileInfo/download")
    @BusinessLog(title = "文件信息表_下载文件", opType = LogAnnotionOpTypeEnum.OTHER)
    public void download(@Validated(SysFileInfoParam.detail.class) SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {
        sysFileInfoService.download(sysFileInfoParam, response);
    }

    /**
     * 文件预览
     *
     * @author stylefeng, xuyuxiang
     * @date 2020/6/9 22:07
     */
    @GetMapping("/sysFileInfo/preview")
    public void preview(@Validated(SysFileInfoParam.detail.class) SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {
        sysFileInfoService.preview(sysFileInfoParam, response);
    }

    /**
     * 分页查询文件信息表
     *
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    @Permission
    @GetMapping("/sysFileInfo/page")
    @BusinessLog(title = "文件信息表_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.page(sysFileInfoParam));
    }

    /**
     * 获取全部文件信息表
     *
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    @Permission
    @GetMapping("/sysFileInfo/list")
    @BusinessLog(title = "文件信息表_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.list(sysFileInfoParam));
    }

    /**
     * 查看详情文件信息表
     *
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    @GetMapping("/sysFileInfo/detail")
    @BusinessLog(title = "文件信息表_查看详情", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysFileInfoParam.detail.class) SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.detail(sysFileInfoParam));
    }

    /**
     * 删除文件信息表
     *
     * @author stylefeng
     * @date 2020/6/7 22:15
     */
    @Permission
    @PostMapping("/sysFileInfo/delete")
    @BusinessLog(title = "文件信息表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysFileInfoParam.delete.class) SysFileInfoParam sysFileInfoParam) {
        sysFileInfoService.delete(sysFileInfoParam);
        return new SuccessResponseData();
    }

    public static String getFileCloudPath(String modelName){
        Calendar cal = Calendar.getInstance();
        return "files/"+modelName+"/"+ cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/";
    }
}