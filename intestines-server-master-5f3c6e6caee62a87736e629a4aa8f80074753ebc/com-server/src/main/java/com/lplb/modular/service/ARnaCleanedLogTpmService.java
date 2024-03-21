package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.ARnaCleanedLogTpm;
import com.lplb.modular.model.query.ARnaCleanedLogTpmQuery;
import com.lplb.modular.model.vo.ARnaCleanedLogTpmVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表服务接口
 */
public interface ARnaCleanedLogTpmService extends IService<ARnaCleanedLogTpm> {

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
    IPage<ARnaCleanedLogTpmVo> pageList(ARnaCleanedLogTpmQuery query);

    /**
     * 通过基因名称查询
     *
     * @param geneName
     * @return
     */
    List<ARnaCleanedLogTpmVo> listByGeneName(String geneName);
}

