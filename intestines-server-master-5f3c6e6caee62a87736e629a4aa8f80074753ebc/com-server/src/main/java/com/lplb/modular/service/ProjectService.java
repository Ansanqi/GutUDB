package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Project;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 17:34:45
 * @Description（描述）：Project（项目）(Project)表服务接口
 */
public interface ProjectService extends IService<Project> {

    /**
     * 通过项目编号查询项目
     *
     * @param projectNo
     * @return
     */
    Project getByProjectNo(String projectNo);

    /**
     * 保存项目
     *
     * @param projectNo   项目编码
     * @param projectName 项目名称
     * @param projectUrl  项目地址
     * @param omicsId     组学ID
     * @param className   类名
     * @param tableName   表名
     * @param fileUrl     文件地址
     * @param fileName    文件名称
     * @param dataId      数据ID
     * @return
     */
    Boolean saveProjectAndReq(String projectNo,
                              String projectName,
                              String projectUrl,
                              Long omicsId,
                              String className,
                              String tableName,
                              String fileUrl,
                              String fileName,
                              Long dataId);
}

