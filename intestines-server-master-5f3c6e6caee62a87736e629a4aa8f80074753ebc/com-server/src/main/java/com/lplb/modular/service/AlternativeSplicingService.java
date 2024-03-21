package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.AlternativeSplicing;
import com.lplb.modular.model.query.AlternativeSplicingQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：Alternative splicing（选择性剪接）(AlternativeSplicing)表服务接口
 */
public interface AlternativeSplicingService extends IService<AlternativeSplicing> {

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
    IPage<AlternativeSplicing> pageList(AlternativeSplicingQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<AlternativeSplicing> listByGeneName(String geneName);

    /**
     * Top 10 Genes for Single-cell Alternative Splicing
     *
     * @return
     */
    Map<String, Integer> top10GenesForSingleCellAlternativeSplicing();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, AlternativeSplicingQuery query);
}

