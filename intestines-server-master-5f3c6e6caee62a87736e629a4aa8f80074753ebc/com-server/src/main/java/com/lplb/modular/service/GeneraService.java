package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Genera;
import com.lplb.modular.model.query.GeneraQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表服务接口
 */
public interface GeneraService extends IService<Genera> {

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
    IPage<Genera> pageList(GeneraQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<Genera> listByGeneName(String geneName, String disease);

    /**
     * Genera数据统计
     *
     * @return
     */
    Map<String, Integer> generaAssociatedWithIntestinalDiseases();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, GeneraQuery query);
}

