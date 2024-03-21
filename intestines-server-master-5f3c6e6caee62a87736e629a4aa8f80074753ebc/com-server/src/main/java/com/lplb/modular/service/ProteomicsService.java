package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Proteomics;
import com.lplb.modular.model.query.ProteomicsQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:43
 * @Description（描述）：Proteomics（蛋白质组学）(Proteomics)表服务接口
 */
public interface ProteomicsService extends IService<Proteomics> {

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
    IPage<Proteomics> pageList(ProteomicsQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<Proteomics> listByGeneName(String geneName, String disease);

    /**
     * 肠道部位统计
     *
     * @return
     */
    Map<String, Integer> position();

    /**
     * Protein数据统计
     *
     * @return
     */
    Map<String, Integer> proteinStatistics();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ProteomicsQuery query);
}

