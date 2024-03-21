package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.GeneExpressionData;
import com.lplb.modular.model.query.GeneExpressionDataQuery;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表服务接口
 */
public interface GeneExpressionDataService extends IService<GeneExpressionData> {

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
    IPage<GeneExpressionDataVo> pageList(GeneExpressionDataQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<GeneExpressionDataVo> listByGeneName(String geneName);
}

