package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SpatialHistology;
import com.lplb.modular.model.query.SpatialHistologyQuery;
import com.lplb.modular.model.vo.SpatialHistologyVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：Spatial histology(SpatialHistology)表服务接口
 */
public interface SpatialHistologyService extends IService<SpatialHistology> {
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
    IPage<SpatialHistologyVo> pageList(SpatialHistologyQuery query);

    /**
     * 详情查询
     *
     * @param id
     * @return
     */
    SpatialHistologyVo details(Long id);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<SpatialHistology> listByGeneName(String geneName);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, SpatialHistologyQuery query);
}

