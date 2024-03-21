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
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.HistoneMapper;
import com.lplb.modular.model.entity.Histone;
import com.lplb.modular.model.export.HistoneExport;
import com.lplb.modular.model.query.HistoneQuery;
import com.lplb.modular.model.vo.HistoneVo;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.FiledColorService;
import com.lplb.modular.service.HistoneService;
import com.lplb.modular.service.TissueCellLineService;
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
 * @Date（日期）： 2023-07-27 18:35:35
 * @Description（描述）：Histone（组蛋白）(Histone)表服务实现类
 */
@Service
@Transactional
public class HistoneServiceImpl extends ServiceImpl<HistoneMapper, Histone> implements HistoneService {

    private final String tableName = "in_histone";
    private final String filePath = "/01.Epigenomics/Histone modification";
    private final String fileName = "Histone";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private TissueCellLineService tissueCellLineService;
    @Resource
    private FiledColorService filedColorService;

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
            List<Histone> list = reader.readAll(Histone.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.EPIGENOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.HISTONE_MODIFICATION.getId());
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
    public IPage<HistoneVo> pageList(HistoneQuery query) {
        // 分页
        Page<Histone> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<Histone> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getHistone())) {
            wrapper.eq(Histone::getHistone, query.getHistone());
        }
        if (ObjectUtils.isNotEmpty(query.getSapiens())) {
            wrapper.eq(Histone::getSapiens, query.getSapiens());
        }
        if (ObjectUtils.isNotEmpty(query.getCellLineTissue())) {
            wrapper.eq(Histone::getCellLineTissue, query.getCellLineTissue());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;

            if ("log10QValue".equals(order[0])) {
                colum = "log_10_q_value";
            } else if ("log10PValue".equals(order[0])) {
                colum = "log_10_p_value";
            } else {
                colum = StringUtils.camelToUnderline(order[0]);
            }

            if (OrderByEnums.ASC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " ASC");
            } else if (OrderByEnums.DESC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " DESC");
            }
        }

        page = this.page(page, wrapper);

        IPage<HistoneVo> result = BeanConvertUtil.pageConvert(page, HistoneVo.class);
        result.getRecords().forEach(item -> {
            // 查询颜色
            String color = filedColorService.findByType("in_histone", "log_10_p_value", String.valueOf(item.getLog10PValue().stripTrailingZeros().toPlainString()));
            item.setColor(color);
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
    public void exportExcel(HttpServletResponse response, HistoneQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<Histone> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getHistone())) {
            wrapper.eq(Histone::getHistone, query.getHistone());
        }
        if (ObjectUtils.isNotEmpty(query.getSapiens())) {
            wrapper.eq(Histone::getSapiens, query.getSapiens());
        }
        if (ObjectUtils.isNotEmpty(query.getCellLineTissue())) {
            wrapper.eq(Histone::getCellLineTissue, query.getCellLineTissue());
        }

        // 查询数据集合
        List<Histone> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<Histone> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<HistoneExport> exports = BeanConvertUtil.listConvert(list, HistoneExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Epigenomics_Histone Modification_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), HistoneExport.class)
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
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<Histone> listByGeneName(String geneName) {
        return null;
    }

    /**
     * Histone统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> histoneStatistics() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.histoneStatistics();

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
        reader.addHeaderAlias("Histone", "histone");
        reader.addHeaderAlias("Sapiens", "sapiens");
        reader.addHeaderAlias("Cell line/tissue", "cellLineTissue");
        reader.addHeaderAlias("Chromosome", "chromosome");
        reader.addHeaderAlias("Name of peak", "nameOfPeak");
        reader.addHeaderAlias("Start position of peak", "startPositionOfPeak");
        reader.addHeaderAlias("End position of peak", "endPositionOfPeak");
        reader.addHeaderAlias("Width of the peak", "widthOfThePeak");
        reader.addHeaderAlias("Peak score", "peakScore");
        reader.addHeaderAlias("Signal value of the peak", "signalValueOfThePeak");
        reader.addHeaderAlias("Strand of the gene", "strandOfTheGene");
        reader.addHeaderAlias(" -log10(q-value)", "log10QValue");
        reader.addHeaderAlias(" -log10(p-value)", "log10PValue");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("histone", "Histone");
        writer.addHeaderAlias("sapiens", "Sapiens");
        writer.addHeaderAlias("cellLineTissue", "Cell line/tissue");
        writer.addHeaderAlias("chromosome", "Chromosome");
        writer.addHeaderAlias("nameOfPeak", "Name of peak");
        writer.addHeaderAlias("startPositionOfPeak", "Start position of peak");
        writer.addHeaderAlias("endPositionOfPeak", "End position of peak");
        writer.addHeaderAlias("widthOfThePeak", "Width of the peak");
        writer.addHeaderAlias("peakScore", "Peak score");
        writer.addHeaderAlias("signalValueOfThePeak", "Signal value of the peak");
        writer.addHeaderAlias("strandOfTheGene", "Strand of the gene");
        writer.addHeaderAlias("log10QValue", " -log10(q-value)");
        writer.addHeaderAlias("log10PValue", " -log10(p-value)");
        return writer;
    }
}

