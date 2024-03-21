package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SeMatsJcec;
import com.lplb.modular.model.query.SeMatsJcecQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SE.MATS.JCEC(SeMatsJcec)表服务接口
 */
public interface SeMatsJcecService extends IService<SeMatsJcec> {

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
    IPage<SeMatsJcec> pageList(SeMatsJcecQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, SeMatsJcecQuery query);
}

