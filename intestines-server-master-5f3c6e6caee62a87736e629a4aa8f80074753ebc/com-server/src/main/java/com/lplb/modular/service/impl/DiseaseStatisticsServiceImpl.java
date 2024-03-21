package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.exception.ServiceException;
import com.lplb.modular.mapper.DiseaseStatisticsMapper;
import com.lplb.modular.model.entity.DiseaseStatistics;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseStatisticsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-07 10:39:03
 * @Description（描述）：疾病统计表(DiseaseStatistics)表服务实现类
 */
@Service
public class DiseaseStatisticsServiceImpl extends ServiceImpl<DiseaseStatisticsMapper, DiseaseStatistics> implements DiseaseStatisticsService {

    @Resource
    private ThreadPoolExecutor executor;


    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    @Override
    public Boolean importExcel(MultipartFile file) {

        InputStream inputStream = null;
        try {
            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 设置表头
            reader = this.readHeaderAlias(reader);

            // 获取列表
            List<DiseaseStatistics> list = reader.readAll(DiseaseStatistics.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 计算该条数据关联的基因个数
                        String diseaseRelatedGenes = item.getDiseaseRelatedGenes();
                        List<String> genes = new ArrayList<>(Arrays.asList(diseaseRelatedGenes.split("\\|")));
                        // 去空
                        genes = genes.stream().filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
//                        item.setGenesCount(genes.size());

                        genes.forEach(i -> {
                            item.setDiseaseRelatedGenes(i);
                            item.setId(null);
                            flag.set(save(item));
                            errorLine.set(list.indexOf(item));
                        });
                        if (!flag.get()) {
                            throw new ServiceException(500, "数据导入失败，失败行数：" + errorLine.get());
                        }
                    }
                });
            });
            return flag.get();
        } catch (IOException e) {
            throw new ServiceException(500, "输入读取异常");
        } finally {
            if (ObjectUtils.isNotEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ServiceException(500, "输入流关闭异常");
                }
            }
        }
    }

    /**
     * 肠道疾病统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> intestinalDiseases() {
        Map<String, Integer> result = new LinkedHashMap<>();
        List<StatisticsVo> list = this.baseMapper.intestinalDiseases();

        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * Top 10 Genes（出现频次前十基因）
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10Genes() {
        Map<String, Integer> result = new LinkedHashMap<>();
        List<StatisticsVo> list = this.baseMapper.top10Genes();
        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Disease related genes", "diseaseRelatedGenes");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Omics", "omics");
        reader.addHeaderAlias("Source", "source");
        return reader;
    }
}

