package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.ProjectMapper;
import com.lplb.modular.model.entity.Project;
import com.lplb.modular.model.entity.ProjectSeq;
import com.lplb.modular.service.ProjectSeqService;
import com.lplb.modular.service.ProjectService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-29 17:34:46
 * @Description（描述）：Project（项目）(Project)表服务实现类
 */
@Service
@Transactional
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Resource
    private ProjectSeqService projectSeqService;

    /**
     * 通过项目编号查询项目
     *
     * @param projectNo
     * @return
     */
    @Override
    public Project getByProjectNo(String projectNo) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectNo, projectNo);
        return this.getOne(wrapper);
    }

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
    @Override
    public synchronized Boolean saveProjectAndReq(String projectNo,
                                                  String projectName,
                                                  String projectUrl,
                                                  Long omicsId,
                                                  String className,
                                                  String tableName,
                                                  String fileUrl,
                                                  String fileName,
                                                  Long dataId) {
        if (ObjectUtils.isEmpty(projectNo) || "NA".equals(projectNo)) {
            return false;
        }

        // 通过项目编号查询项目
        Project project = this.getByProjectNo(projectNo);
        if (ObjectUtils.isEmpty(project)) {
            project = new Project();
            project.setProjectNo(projectNo);
            project.setName(projectName);
            project.setFrequency(1);
            project.setJumpUrl(projectUrl);
            this.save(project);
        } else {
            project.setFrequency(project.getFrequency() + 1);
            this.updateById(project);
        }

        // 新增出现记录
        ProjectSeq seq = new ProjectSeq();
        seq.setProjectId(project.getId());
        seq.setOmicsId(omicsId);
        seq.setEntityName(className);
        seq.setTableName(tableName);
        seq.setFileUrl(fileUrl);
        seq.setFileName(fileName);
        seq.setDataId(dataId);
        projectSeqService.save(seq);
        return true;
    }
}

