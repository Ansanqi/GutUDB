package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.TcgaCoadExp;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-16 19:08:44
 * @Description（描述）：TCGA COAD_exp(TcgaCoadExp)表服务接口
 */
public interface TcgaCoadExpService extends IService<TcgaCoadExp> {

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
    List<GeneExpressionDataVo> listByGeneName(String geneName);
}

