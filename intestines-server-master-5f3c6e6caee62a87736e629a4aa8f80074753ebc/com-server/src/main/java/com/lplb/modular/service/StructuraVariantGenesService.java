package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.StructuraVariantGenes;
import com.lplb.modular.model.query.StructuraVariantGenesQuery;
import com.lplb.modular.model.vo.StructuraVariantGenesVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Structural Variant Genes（结构变异基因）(StructuraVariantGenes)表服务接口
 */
public interface StructuraVariantGenesService extends IService<StructuraVariantGenes> {

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
    IPage<StructuraVariantGenesVo> pageList(StructuraVariantGenesQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, StructuraVariantGenesQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<StructuraVariantGenes> listByGeneName(String geneName, String disease);

    /**
     * Top 10 Structural Variant Genes
     *
     * @return
     */
    Map<String, Integer> top10StructuralVariantGenes();
}

