package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lplb.modular.model.query.MousecolonRnaGeneDetailsQuery;
import com.lplb.modular.model.query.MousecolonRnaQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 11:02
 * @Description（描述）：MousecolonRnaService
 */
public interface MousecolonRnaService {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 列表获取
     *
     * @param query
     * @return
     */
    IPage<Map<String, Object>> pageList(MousecolonRnaQuery query);

    /**
     * 详情
     *
     * @param query
     * @return
     */
    Map<String, Object> details(MousecolonRnaGeneDetailsQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    String exportExcel(HttpServletResponse response);
}
