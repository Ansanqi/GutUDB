package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.VirulenceGene;
import com.lplb.modular.model.query.VirulenceGeneQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Virulence gene（毒性基因）(VirulenceGene)表服务接口
 */
public interface VirulenceGeneService extends IService<VirulenceGene> {

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
    IPage<VirulenceGene> pageList(VirulenceGeneQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, VirulenceGeneQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<VirulenceGene> listByGeneName(String geneName, String disease);

    /**
     * Top 10 Virulence Genes
     *
     * @return
     */
    Map<String, Integer> top10VirulenceGenes();
}

