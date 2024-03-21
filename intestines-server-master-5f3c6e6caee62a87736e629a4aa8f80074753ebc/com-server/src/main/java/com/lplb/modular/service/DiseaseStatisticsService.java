package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.DiseaseStatistics;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-07 10:39:03
 * @Description（描述）：疾病统计表(DiseaseStatistics)表服务接口
 */
public interface DiseaseStatisticsService extends IService<DiseaseStatistics> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 肠道疾病统计
     *
     * @return
     */
    Map<String, Integer> intestinalDiseases();

    /**
     * Top 10 Genes（出现频次前十基因）
     *
     * @return
     */
    Map<String, Integer> top10Genes();
}

