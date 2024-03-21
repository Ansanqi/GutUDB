package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.A5ssMatsJcec;
import com.lplb.modular.model.query.A5ssMatsJcecQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A5SS.MATS.JCEC(A5ssMatsJcec)表服务接口
 */
public interface A5ssMatsJcecService extends IService<A5ssMatsJcec> {

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
    IPage<A5ssMatsJcec> pageList(A5ssMatsJcecQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, A5ssMatsJcecQuery query);
}

