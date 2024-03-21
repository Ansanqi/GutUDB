package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Snp;
import com.lplb.modular.model.query.SnpQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：SNP(Snp)表服务接口
 */
public interface SnpService extends IService<Snp> {

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
    IPage<Snp> pageList(SnpQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, SnpQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<Snp> listByGeneName(String geneName, String disease);

    /**
     * Top 10 SNP
     *
     * @return
     */
    Map<String, Integer> top10Snp();
}

