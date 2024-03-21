package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.GeoInformation;
import com.lplb.modular.model.query.GeoInformationQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：GEO_information(GeoInformation)表服务接口
 */
public interface GeoInformationService extends IService<GeoInformation> {

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
    IPage<GeoInformation> pageList(GeoInformationQuery query);

    /**
     * 根据项目编号查询
     *
     * @param projectNo
     * @return
     */
    GeoInformation getByProject(String projectNo);

    /**
     * 物种统计
     *
     * @return
     */
    Map<String, Integer> species();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, GeoInformationQuery query);
}

