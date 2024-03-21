package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ChemicalCompounds;
import com.lplb.modular.model.query.ChemicalCompoundsQuery;
import com.lplb.modular.model.vo.TherapyStatisticsVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chemical compounds（化合物）(ChemicalCompounds)表服务接口
 */
public interface ChemicalCompoundsService extends IService<ChemicalCompounds> {

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
    IPage<ChemicalCompounds> pageList(ChemicalCompoundsQuery query);

    /**
     * 根据基因名称查询西药列表
     *
     * @param geneName
     * @param disease
     * @return
     */
    List<ChemicalCompounds> listByGene(String geneName, String disease);

    /**
     * Chemical Compounds数据统计
     *
     * @return
     */
    TherapyStatisticsVo chemicalCompoundsStatistics(TherapyStatisticsVo therapyStatistics);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ChemicalCompoundsQuery query);
}

