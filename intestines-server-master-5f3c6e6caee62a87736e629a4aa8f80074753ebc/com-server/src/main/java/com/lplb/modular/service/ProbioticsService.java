package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Probiotics;
import com.lplb.modular.model.query.ProbioticsQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表服务接口
 */
public interface ProbioticsService extends IService<Probiotics> {

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
    IPage<Probiotics> pageList(ProbioticsQuery query);

    /**
     * Probiotics统计
     *
     * @return
     */
    Map<String, Integer> probioticsStatistics();

    /**
     * Top 10 Probiotics associated with Intestinal Diseases
     *
     * @return
     */
    Map<String, Integer> top10ProbioticsAssociatedWithIntestinalDiseases();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ProbioticsQuery query);
}

