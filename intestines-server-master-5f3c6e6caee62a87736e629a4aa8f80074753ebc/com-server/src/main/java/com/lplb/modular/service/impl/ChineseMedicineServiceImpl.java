package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.ChineseMedicineMapper;
import com.lplb.modular.model.entity.ChineseMedicine;
import com.lplb.modular.model.export.ChineseMedicineExport;
import com.lplb.modular.model.query.ChineseMedicineQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.ChineseMedicineService;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:29
 * @Description（描述）：Chinese Medicine（中药）(ChineseMedicine)表服务实现类
 */
@Service
@Transactional
public class ChineseMedicineServiceImpl extends ServiceImpl<ChineseMedicineMapper, ChineseMedicine> implements ChineseMedicineService {

    private final String tableName = "in_chinese_medicine";
    private final String filePath = "/09.Therapy/Chinese Medicine";
    private final String fileName = "Chinese_Medicine";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private DiseaseService diseaseService;

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
            List<ChineseMedicine> list = reader.readAll(ChineseMedicine.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        flag.set(save(item));

                        errorLine.set(list.indexOf(item));
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
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<ChineseMedicine> pageList(ChineseMedicineQuery query) {
        // 分页
        Page<ChineseMedicine> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<ChineseMedicine> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(ChineseMedicine::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(ChineseMedicine::getDisease, query.getDisease());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum = StringUtils.camelToUnderline(order[0]);

            if (OrderByEnums.ASC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " ASC");
            } else if (OrderByEnums.DESC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " DESC");
            }
        }
        page = this.page(page, wrapper);
        return page;
    }

    /**
     * 根据基因名称查询中药列表
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public List<ChineseMedicine> listByGene(String geneName, String disease) {
        LambdaQueryWrapper<ChineseMedicine> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ChineseMedicine::getDiseaseRelatedGenes, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.like(ChineseMedicine::getDisease, disease);
        }

        List<ChineseMedicine> result = this.list(wrapper);
        return result;
    }

    /***
     * Chinese Medicine数据统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> chineseMedicineStatistics() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.chineseMedicineStatistics();
        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, ChineseMedicineQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<ChineseMedicine> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(ChineseMedicine::getDiseaseRelatedGenes, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(ChineseMedicine::getDisease, query.getDisease());
        }

        // 查询数据集合
        List<ChineseMedicine> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<ChineseMedicine> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ChineseMedicineExport> exports = BeanConvertUtil.listConvert(list, ChineseMedicineExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Home_Therapy_Traditional Medicine_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ChineseMedicineExport.class)
                    .excelType(ExcelTypeEnum.CSV)
                    .sheet(fileName)
                    .doWrite(exports);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件编码异常");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件导出异常");
        }

    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Disease related genes", "diseaseRelatedGenes");
        reader.addHeaderAlias("Herbs Associated with This Disease", "herbsAssociatedWithThisDisease");
        reader.addHeaderAlias("link", "herbsAssociatedWithThisDiseaseUrl");
        return reader;
    }

    /**
     * 写入表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("diseaseRelatedGenes", "Disease related genes");
        writer.addHeaderAlias("herbsAssociatedWithThisDisease", "Herbs Associated with This Disease");
        writer.addHeaderAlias("herbsAssociatedWithThisDiseaseUrl", "link");
        return writer;
    }
}

