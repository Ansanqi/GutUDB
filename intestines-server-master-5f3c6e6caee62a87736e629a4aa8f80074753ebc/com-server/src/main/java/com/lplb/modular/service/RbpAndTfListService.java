package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.RbpAndTfList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-01 16:16:05
 * @Description（描述）：RBP and TF list表(RbpAndTfList)表服务接口
 */
public interface RbpAndTfListService extends IService<RbpAndTfList> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    RbpAndTfList getByGeneName(String geneName);

    /**
     * 首页搜索
     *
     * @param searchText
     * @return
     */
    List<RbpAndTfList> firstPageSearch(String searchText);
}

