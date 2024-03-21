package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SingleCellGeneExpressData;
import com.lplb.modular.model.query.SingleCellGeneExpressDataQuery;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataUniqueVo;
import com.lplb.modular.model.vo.SingleCellGeneExpressDataVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据详情(SingleCellGeneExpressData)表服务接口
 */
public interface SingleCellGeneExpressDataService extends IService<SingleCellGeneExpressData> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 查询唯一键数据列表数据
     *
     * @return
     */
    List<SingleCellGeneExpressDataUniqueVo> uniqueIdList();

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    IPage<SingleCellGeneExpressDataVo> pageList(SingleCellGeneExpressDataQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<SingleCellGeneExpressData> listByGeneName(String geneName);

    /**
     * Cluster数据统计
     *
     * @return
     */
    Map<String, Integer> clusterStatistics();

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, SingleCellGeneExpressDataQuery query);
}

