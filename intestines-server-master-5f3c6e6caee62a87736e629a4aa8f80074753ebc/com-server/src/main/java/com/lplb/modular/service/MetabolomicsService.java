package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Metabolomics;
import com.lplb.modular.model.query.MetabolomicsQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Metabolomics（代谢组学）(Metabolomics)表服务接口
 */
public interface MetabolomicsService extends IService<Metabolomics> {

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
    IPage<Metabolomics> pageList(MetabolomicsQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<Metabolomics> listByGeneName(String geneName, String disease);

    /**
     * Metabolomics数据统计
     *
     * @return
     */
    Map<String, Integer> metaboliteStatistics();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, MetabolomicsQuery query);
}

