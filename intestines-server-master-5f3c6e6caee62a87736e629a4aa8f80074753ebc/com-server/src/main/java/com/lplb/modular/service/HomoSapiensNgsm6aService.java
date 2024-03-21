package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.HomoSapiensNgsm6a;
import com.lplb.modular.model.query.HomoSapiensNgsm6aQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：HomoSapiens_NGSm6A(HomoSapiensNgsm6a)表服务接口
 */
public interface HomoSapiensNgsm6aService extends IService<HomoSapiensNgsm6a> {

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
    IPage<HomoSapiensNgsm6a> pageList(HomoSapiensNgsm6aQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<HomoSapiensNgsm6a> listByGeneName(String geneName);

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    void exportExcel(HttpServletResponse response, HomoSapiensNgsm6aQuery query);
}

