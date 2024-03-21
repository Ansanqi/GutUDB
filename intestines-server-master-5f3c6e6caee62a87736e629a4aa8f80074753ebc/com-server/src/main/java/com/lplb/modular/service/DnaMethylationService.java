package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.DnaMethylation;
import com.lplb.modular.model.query.DnaMethylationQuery;
import com.lplb.modular.model.vo.DnaMethylationVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:30
 * @Description（描述）：DNA methylation（DNA甲基化）(DnaMethylation)表服务接口
 */
public interface DnaMethylationService extends IService<DnaMethylation> {

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
    IPage<DnaMethylationVo> pageList(DnaMethylationQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, DnaMethylationQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<DnaMethylation> listByGeneName(String geneName, String disease);

    /**
     * Top 10 Genes in DNA Methylation
     *
     * @return
     */
    Map<String, Integer> top10GenesInDnaMethylation();
}

