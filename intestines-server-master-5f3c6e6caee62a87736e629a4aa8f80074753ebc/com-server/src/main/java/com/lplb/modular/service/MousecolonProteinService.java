package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.MousecolonProtein;
import com.lplb.modular.model.query.MousecolonProteinQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-11 09:42:06
 * @Description（描述）：GSM6578068_mousecolon_protein(MousecolonProtein)表服务接口
 */
public interface MousecolonProteinService extends IService<MousecolonProtein> {

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
    IPage<Map<String, Object>> pageList(MousecolonProteinQuery query);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    Map<String, Object> details(Long id);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, MousecolonProteinQuery query);
}

