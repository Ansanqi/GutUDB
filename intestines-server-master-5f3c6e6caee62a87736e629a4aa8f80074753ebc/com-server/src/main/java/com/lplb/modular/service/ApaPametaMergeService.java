package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ApaPametaMerge;
import com.lplb.modular.model.query.ApaPametaMergeQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：APA-pameta-merge(ApaPametaMerge)表服务接口
 */
public interface ApaPametaMergeService extends IService<ApaPametaMerge> {

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
    IPage<ApaPametaMerge> pageList(ApaPametaMergeQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<ApaPametaMerge> listByGeneName(String geneName);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, ApaPametaMergeQuery query);
}
