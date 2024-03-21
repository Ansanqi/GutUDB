package com.lplb.modular.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.DRnaTsScore;
import com.lplb.modular.model.query.DRnaTsScoreQuery;
import com.lplb.modular.model.vo.DRnaTsScoreVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：D RNA TS score(DRnaTsScore)表服务接口
 */
public interface DRnaTsScoreService extends IService<DRnaTsScore> {

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
    IPage<DRnaTsScoreVo> pageList(DRnaTsScoreQuery query);

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    List<DRnaTsScoreVo> listByGeneName(String geneName);
}

