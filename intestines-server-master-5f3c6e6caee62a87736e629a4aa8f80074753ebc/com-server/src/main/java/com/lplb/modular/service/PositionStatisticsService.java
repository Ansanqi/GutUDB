package com.lplb.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lplb.modular.model.entity.PositionStatistics;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-08 11:42:05
 * @Description（描述）：Position统计表(PositionStatistics)表服务接口
 */
public interface PositionStatisticsService extends IService<PositionStatistics> {

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    Boolean importExcel(MultipartFile file);

    /**
     * 肠道部位统计
     *
     * @return
     */
    Map<String, Integer> position();
}

