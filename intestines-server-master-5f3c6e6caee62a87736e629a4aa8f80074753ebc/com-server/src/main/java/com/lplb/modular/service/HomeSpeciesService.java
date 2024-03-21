package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.HomeSpecies;
import com.lplb.modular.model.query.HomeSpeciesQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-04 23:02:23
 * @Description（描述）：Home | Species(HomeSpecies)表服务接口
 */
public interface HomeSpeciesService extends IService<HomeSpecies> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    IPage<HomeSpecies> pageList(HomeSpeciesQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, HomeSpeciesQuery query);
}

