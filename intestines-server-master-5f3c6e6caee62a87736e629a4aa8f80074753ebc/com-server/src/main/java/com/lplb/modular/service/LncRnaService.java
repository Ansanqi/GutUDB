package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.LncRna;
import com.lplb.modular.model.query.LncRnaQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：lnc RNA（长非编码核糖核酸）(LncRna)表服务接口
 */
public interface LncRnaService extends IService<LncRna> {

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
    IPage<LncRna> pageList(LncRnaQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<LncRna> listByGeneName(String geneName, String disease);

    /**
     * Top 10 lnc RNA
     *
     * @return
     */
    Map<String, Integer> top10LncRna();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, LncRnaQuery query);
}

