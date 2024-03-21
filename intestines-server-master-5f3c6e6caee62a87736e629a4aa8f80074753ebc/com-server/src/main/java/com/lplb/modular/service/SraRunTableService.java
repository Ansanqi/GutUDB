package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.SraRunTable;
import com.lplb.modular.model.query.SraRunTableQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表服务接口
 */
public interface SraRunTableService extends IService<SraRunTable> {

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
    IPage<SraRunTable> pageList(SraRunTableQuery query);

    /**
     * 分组信息查询
     *
     * @param dataAccessId
     * @return
     */
    List<Map<String, Object>> groupInfos(String dataAccessId);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, SraRunTableQuery query);

    /**
     * 根据dataAccessId查询数据列表
     *
     * @param dataAccessId
     * @return
     */
    List<SraRunTable> listByByDataAccessId(String dataAccessId);

    /**
     * 分组Note信息查询
     *
     * @param dataAccessId
     * @return
     */
    String groupNotes(String dataAccessId);
}

