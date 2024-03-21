package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.MutatedGenes;
import com.lplb.modular.model.query.MutatedGenesQuery;
import com.lplb.modular.model.vo.MutatedGenesVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：Mutated Genes（突变基因）(MutatedGenes)表服务接口
 */
public interface MutatedGenesService extends IService<MutatedGenes> {

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
    IPage<MutatedGenesVo> pageList(MutatedGenesQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, MutatedGenesQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<MutatedGenes> listByGeneName(String geneName, String disease);

    /**
     * Top 10 Mutated Genes
     *
     * @return
     */
    Map<String, Integer> top10MutatedGenes();
}

