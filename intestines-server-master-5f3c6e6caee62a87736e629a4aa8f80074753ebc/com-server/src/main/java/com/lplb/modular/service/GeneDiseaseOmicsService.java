package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.GeneDiseaseOmics;
import com.lplb.modular.model.query.GeneDiseaseOmicsQuery;
import com.lplb.modular.model.vo.ColonDiseaseSearchHeaderVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-05 00:22:17
 * @Description（描述）：Gene_disease_omics(GeneDiseaseOmics)表服务接口
 */
public interface GeneDiseaseOmicsService extends IService<GeneDiseaseOmics> {

    /**
     * Colon Disease表头搜索数据
     *
     * @return
     */
    ColonDiseaseSearchHeaderVo colonDiseaseSearchHeader();

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
    IPage<GeneDiseaseOmics> pageList(GeneDiseaseOmicsQuery query);

    /**
     * 肠道疾病统计
     *
     * @return
     */
    Map<String, Integer> intestinalDiseases();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, GeneDiseaseOmicsQuery query);
}

