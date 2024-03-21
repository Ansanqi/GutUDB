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
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.GeneraMapper;
import com.lplb.modular.model.entity.Genera;
import com.lplb.modular.model.entity.Proteomics;
import com.lplb.modular.model.export.GeneraExport;
import com.lplb.modular.model.export.ProteomicsExport;
import com.lplb.modular.model.query.GeneraQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneraService;
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
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Genera（属群）(Genera)表服务实现类
 */
@Service
@Transactional
public class GeneraServiceImpl extends ServiceImpl<GeneraMapper, Genera> implements GeneraService {

    private final String tableName = "in_genera";
    private final String filePath = "/07.Microbiome";
    private final String fileName = "Genera";

    @Resource
    private ThreadPoolExecutor executor;
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
            List<Genera> list = reader.readAll(Genera.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.MICROBIOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.MICROBI_OMICS.getId());
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
                        // 该数据关系到组织细胞系，统计放入数据库
//                        diseaseService.savaDiseaseAndSeq(
//                                item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.MICROBIOMICS.getId(),
//                                Genera.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );

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
    public IPage<Genera> pageList(GeneraQuery query) {
        // 分页
        Page<Genera> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<Genera> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearch())) {
//            wrapper.like(Genera::getBodySite, query.getSearch())
//                    .or()
//                    .like(Genera::getDisease, query.getSearch())
//                    .or()
//                    .like(Genera::getGeneraName, query.getSearch());
//        }
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Genera::getGeneraName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Genera::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getBodySite())) {
            wrapper.eq(Genera::getBodySite, query.getBodySite());
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
     * 根据基因名称查询
     *
     * @param geneName
     * @param disease
     * @return
     */
    @Override
    public List<Genera> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<Genera> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Genera::getGeneraName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(Genera::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Genera数据统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> generaAssociatedWithIntestinalDiseases() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.generaAssociatedWithIntestinalDiseases();
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
    public void exportExcel(HttpServletResponse response, GeneraQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<Genera> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(Genera::getGeneraName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(Genera::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getBodySite())) {
            wrapper.eq(Genera::getBodySite, query.getBodySite());
        }

        // 查询数据集合
        List<Genera> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<Genera> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<GeneraExport> exports = BeanConvertUtil.listConvert(list, GeneraExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Microbiomics_Associated Genera_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), GeneraExport.class)
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
        reader.addHeaderAlias("Body site", "bodySite");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Genera name", "generaName");
        reader.addHeaderAlias("ifGmrepo", "ifGmrepo");
        reader.addHeaderAlias("ifMvp", "ifMvp");
        reader.addHeaderAlias("loaded_uid_num", "loadedUidNum");
        reader.addHeaderAlias("mvpData", "mvpData");
        reader.addHeaderAlias("ncbi_taxon_id", "ncbiTaxonId");
        reader.addHeaderAlias("relative_abundance_avg", "relativeAbundanceAvg");
        reader.addHeaderAlias("relative_abundance_med", "relativeAbundanceMed");
        reader.addHeaderAlias("relative_abundance_std", "relativeAbundanceStd");
        reader.addHeaderAlias("relative_abundance_sum", "relativeAbundanceSum");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("bodySite", "Body site");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("generaName", "Genera name");
        writer.addHeaderAlias("ifGmrepo", "ifGmrepo");
        writer.addHeaderAlias("ifMvp", "ifMvp");
        writer.addHeaderAlias("loadedUidNum", "loaded_uid_num");
        writer.addHeaderAlias("mvpData", "mvpData");
        writer.addHeaderAlias("ncbiTaxonId", "ncbi_taxon_id");
        writer.addHeaderAlias("relativeAbundanceAvg", "relative_abundance_avg");
        writer.addHeaderAlias("relativeAbundanceMed", "relative_abundance_med");
        writer.addHeaderAlias("relativeAbundanceStd", "relative_abundance_std");
        writer.addHeaderAlias("relativeAbundanceSum", "relative_abundance_sum");
        return writer;
    }

}

