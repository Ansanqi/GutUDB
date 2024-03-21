package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.CnaGenes;
import com.lplb.modular.model.query.CnaGenesQuery;
import com.lplb.modular.model.vo.CnaGenesVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：CNA Genes（CAN基因）(CnaGenes)表服务接口
 */
public interface CnaGenesService extends IService<CnaGenes> {

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
    IPage<CnaGenesVo> pageList(CnaGenesQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, CnaGenesQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<CnaGenes> listByGeneName(String geneName, String disease);

    /**
     * Top 10 CNA Genes
     *
     * @return
     */
    Map<String, Integer> top10CnaGenes();
}

