package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ChromosomeStructure;
import com.lplb.modular.model.query.ChromosomeStructureQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chromosome structure（染色体结构）(ChromosomeStructure)表服务接口
 */
public interface ChromosomeStructureService extends IService<ChromosomeStructure> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    IPage<ChromosomeStructure> pageList(ChromosomeStructureQuery query);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ChromosomeStructureQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<ChromosomeStructure> listByGeneName(String geneName);
}

