package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.GeoInfo;
import com.lplb.modular.model.query.GeoInfoQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：GEO_info（高通量基因表达信息）(GeoInfo)表服务接口
 */
public interface GeoInfoService extends IService<GeoInfo> {

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
    IPage<GeoInfo> pageList(GeoInfoQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, GeoInfoQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<GeoInfo> listByGeneName(String geneName);
}

