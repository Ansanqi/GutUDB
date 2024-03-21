package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.exception.ServiceException;
import com.lplb.modular.mapper.PositionStatisticsMapper;
import com.lplb.modular.model.entity.PositionStatistics;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.PositionStatisticsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-08 11:42:05
 * @Description（描述）：Position统计表(PositionStatistics)表服务实现类
 */
@Service
public class PositionStatisticsServiceImpl extends ServiceImpl<PositionStatisticsMapper, PositionStatistics> implements PositionStatisticsService {

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
            List<PositionStatistics> list = reader.readAll(PositionStatistics.class);

//            // 保存结果
//            AtomicBoolean flag = new AtomicBoolean(true);
//            // 出错行
//            AtomicInteger errorLine = new AtomicInteger();
//
//            list.forEach(item -> {
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 计算该条数据关联的基因个数
//                        String diseaseRelatedGenes = item.getDiseaseRelatedGenes();
//                        List<String> genes = new ArrayList<>(Arrays.asList(diseaseRelatedGenes.split("\\|")));
//                        // 去空
//                        genes = genes.stream().filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
//                        item.setGenesCount(genes.size());
//
//                        flag.set(save(item));
//                        errorLine.set(list.indexOf(item));
//                        if (!flag.get()) {
//                            throw new ServiceException(500, "数据导入失败，失败行数：" + errorLine.get());
//                        }
//                    }
//                });
//            });
            return this.saveBatch(list);
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
     * 肠道部位统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> position() {
        Map<String, Integer> result = new LinkedHashMap<>();
        List<StatisticsVo> list = this.baseMapper.position();
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
        reader.addHeaderAlias("Position", "positionName");
        return reader;
    }
}

