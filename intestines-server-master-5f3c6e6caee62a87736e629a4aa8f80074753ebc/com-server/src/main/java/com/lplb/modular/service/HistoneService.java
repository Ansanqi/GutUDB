package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.Histone;
import com.lplb.modular.model.query.HistoneQuery;
import com.lplb.modular.model.vo.HistoneVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)表服务接口
 */
public interface HistoneService extends IService<Histone> {

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
    IPage<HistoneVo> pageList(HistoneQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, HistoneQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<Histone> listByGeneName(String geneName);

    /**
     * Histone统计
     *
     * @return
     */
    Map<String, Integer> histoneStatistics();
}

