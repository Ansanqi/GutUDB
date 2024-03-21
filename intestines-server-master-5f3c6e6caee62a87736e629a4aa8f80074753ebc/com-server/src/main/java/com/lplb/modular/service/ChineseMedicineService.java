package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ChineseMedicine;
import com.lplb.modular.model.query.ChineseMedicineQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表服务接口
 */
public interface ChineseMedicineService extends IService<ChineseMedicine> {

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
    IPage<ChineseMedicine> pageList(ChineseMedicineQuery query);

    /**
     * 根据基因名称查询中药列表
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<ChineseMedicine> listByGene(String geneName, String disease);

    /**
     * Chinese Medicine数据统计
     *
     * @return
     */
    Map<String, Integer> chineseMedicineStatistics();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ChineseMedicineQuery query);
}

