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
import com.lplb.core.consts.PmDataConstant;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.ProbioticsMapper;
import com.lplb.modular.model.entity.Probiotics;
import com.lplb.modular.model.export.ProbioticsExport;
import com.lplb.modular.model.query.ProbioticsQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.PmDataService;
import com.lplb.modular.service.ProbioticsService;
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
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:38
 * @Description（描述）：Probiotics（益生菌）(Probiotics)表服务实现类
 */
@Service
@Transactional
public class ProbioticsServiceImpl extends ServiceImpl<ProbioticsMapper, Probiotics> implements ProbioticsService {

    private final String tableName = "in_probiotics";
    private final String filePath = "/09.Therapy/Probiotics";
    private final String fileName = "Probiotics";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private PmDataService pmDataService;

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
            List<Probiotics> list = reader.readAll(Probiotics.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // pmid处理
                        if (ObjectUtils.isNotEmpty(item.getPmid()) && !"NA".equals(item.getPmid())) {
                            item.setPmidUrl(PmDataConstant.PMID_BASE_URL + item.getPmid());
                        }
                        // 疾病处理
                        if (ObjectUtils.isNotEmpty(item.getDisease()) && !"NA".equals(item.getDisease())) {
                            List<String> diseases = new ArrayList<>(Arrays.asList(item.getDisease().split("、")));
                            // 去空，去空格
                            diseases = diseases.stream().map(i -> i.trim()).filter(i -> ObjectUtils.isNotEmpty(i)).collect(Collectors.toList());
                            // 重新拼接保存
                            item.setDisease(diseases.stream().collect(Collectors.joining(",")));
                        }
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 疾病
//                        if (ObjectUtils.isNotEmpty(item.getDisease()) && !"NA".equals(item.getDisease())) {
//                            List<String> diseases = new ArrayList<>(Arrays.asList(item.getDisease().split(",")));
//                            diseases.forEach(d -> {
//                                diseaseService.savaDiseaseAndSeq(d,
//                                        d,
//                                        null,
//                                        Probiotics.class.getName(),
//                                        tableName,
//                                        filePath,
//                                        file.getOriginalFilename(),
//                                        item.getId());
//                            });
//                        }
//                        // PMID
//                        if (ObjectUtils.isNotEmpty(item.getPmid()) && !"NA".equals(item.getPmid())) {
//                            pmDataService.savePmData(null,
//                                    null,
//                                    item.getId(),
//                                    PmDataType.PROBIOTICS.getName(),
//                                    item.getPmid(),
//                                    PmDataConstant.PMID_BASE_URL + item.getPmid());
//                        }
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
    public IPage<Probiotics> pageList(ProbioticsQuery query) {
        // 分页
        Page<Probiotics> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<Probiotics> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(Probiotics::getProbiotics, query.getSearch())
//                    .or()
//                    .like(Probiotics::getDisease, query.getSearch())
//                    .or()
//                    .like(Probiotics::getGenus, query.getSearch())
//                    .or()
//                    .like(Probiotics::getLocation, query.getSearch())
//                    .or()
//                    .like(Probiotics::getFunction, query.getSearch())
//                    .or()
//                    .like(Probiotics::getPmid, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getProbiotics())) {
            wrapper.eq(Probiotics::getProbiotics, query.getProbiotics());
        }
        wrapper.eq(ObjectUtils.isNotEmpty(query.getDisease()), Probiotics::getDisease, query.getDisease());
        wrapper.eq(ObjectUtils.isNotEmpty(query.getGenus()), Probiotics::getGenus, query.getGenus());
        wrapper.eq(ObjectUtils.isNotEmpty(query.getLocation()), Probiotics::getLocation, query.getLocation());

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
     * Probiotics统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> probioticsStatistics() {
        Map<String, Integer> result = new HashMap<>();

        List<StatisticsVo> list = this.baseMapper.probioticsStatistics();
        list.forEach(item -> {
            result.put(item.getKey(), item.getValue());
        });
        return result;
    }

    /**
     * Top 10 Probiotics associated with Intestinal Diseases
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10ProbioticsAssociatedWithIntestinalDiseases() {
        Map<String, Integer> result = new HashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10ProbioticsAssociatedWithIntestinalDiseases();
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
    public void exportExcel(HttpServletResponse response, ProbioticsQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<Probiotics> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getProbiotics())) {
            wrapper.eq(Probiotics::getProbiotics, query.getProbiotics());
        }
        wrapper.eq(ObjectUtils.isNotEmpty(query.getDisease()), Probiotics::getDisease, query.getDisease());
        wrapper.eq(ObjectUtils.isNotEmpty(query.getGenus()), Probiotics::getGenus, query.getGenus());
        wrapper.eq(ObjectUtils.isNotEmpty(query.getLocation()), Probiotics::getLocation, query.getLocation());

        // 查询数据集合
        List<Probiotics> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<Probiotics> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<ProbioticsExport> exports = BeanConvertUtil.listConvert(list, ProbioticsExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Home_Therapy_Probiotics_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), ProbioticsExport.class)
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
        reader.addHeaderAlias("Probiotics", "probiotics");
        reader.addHeaderAlias("Genus", "genus");
        reader.addHeaderAlias("Location", "location");
        reader.addHeaderAlias("Function", "function");
        reader.addHeaderAlias("PMID", "pmid");
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
        writer.addHeaderAlias("probiotics", "Probiotics");
        writer.addHeaderAlias("genus", "Genus");
        writer.addHeaderAlias("location", "Location");
        writer.addHeaderAlias("function", "Function");
        writer.addHeaderAlias("pmid", "PMID");
        writer.addHeaderAlias("pmidUrl", "pmidUrl");
        return writer;
    }
}

