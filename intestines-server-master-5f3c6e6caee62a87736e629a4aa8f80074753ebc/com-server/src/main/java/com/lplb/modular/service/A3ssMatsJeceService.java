package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.A3ssMatsJece;
import com.lplb.modular.model.query.A3ssMatsJeceQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A3SS.MATS.JCEC(A3ssMatsJece)表服务接口
 */
public interface A3ssMatsJeceService extends IService<A3ssMatsJece> {

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
    IPage<A3ssMatsJece> pageList(A3ssMatsJeceQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, A3ssMatsJeceQuery query);
}

