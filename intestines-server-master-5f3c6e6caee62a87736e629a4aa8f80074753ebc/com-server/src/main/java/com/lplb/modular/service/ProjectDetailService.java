package com.lplb.modular.service;

import com.lplb.modular.model.vo.ProjectDetailVo;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-03 22:31
 * @Description（描述）：ProjectDetailService
 */
public interface ProjectDetailService {

    /**
     * 根据项目编号查询项目详情
     *
     * @param projectNo
     * @return
     */
    ProjectDetailVo projectDetailByProjectNo(String projectNo);
}
