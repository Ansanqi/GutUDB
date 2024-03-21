package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.MxeMatsJcec;
import com.lplb.modular.model.query.MxeMatsJcecQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：MXE.MATS.JCEC(MxeMatsJcec)表服务接口
 */
public interface MxeMatsJcecService extends IService<MxeMatsJcec> {

    /**
     * Excel导入
     *
     * @param file
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    IPage<MxeMatsJcec> pageList(MxeMatsJcecQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, MxeMatsJcecQuery query);
}

