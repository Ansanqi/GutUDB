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
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.ExcelGlobalStyleUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.CircRnaMapper;
import com.lplb.modular.model.entity.CircRna;
import com.lplb.modular.model.entity.GeoInformation;
import com.lplb.modular.model.export.CircRnaExport;
import com.lplb.modular.model.export.GeoInformationExport;
import com.lplb.modular.model.query.CircRnaQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.*;
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
 * @Description（描述）：circRNA（环核糖核酸）(CircRna)表服务实现类
 */
@Service
@Transactional
public class CircRnaServiceImpl extends ServiceImpl<CircRnaMapper, CircRna> implements CircRnaService {

    private final String tableName = "in_circ_rna";
    private final String filePath = "/03.Transcriptomic/Non-coding RNA";
    private final String fileName = "circRNA";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private SampleService sampleService;
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
            List<CircRna> list = reader.readAll(CircRna.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.TRANSCRIPTOMIC.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.NON_CODING_RNA.getId());
                        // pmid处理
                        if (ObjectUtils.isNotEmpty(item.getPmid())) {
                            String pmidUrl = PmDataConstant.PMID_BASE_URL + item.getPmid();
                            item.setPmidUrl(pmidUrl);
                        }

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
    public IPage<CircRna> pageList(CircRnaQuery query) {
        // 分页
        Page<CircRna> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<CircRna> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(CircRna::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(CircRna::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(CircRna::getSpecies, query.getSpecies());
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
    public List<CircRna> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<CircRna> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircRna::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(CircRna::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Top 10 circRNA
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10CircRna() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10CircRna();
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
    public void exportExcel(HttpServletResponse response, CircRnaQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<CircRna> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(CircRna::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(CircRna::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(CircRna::getSpecies, query.getSpecies());
        }

        // 查询数据集合
        List<CircRna> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<CircRna> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<CircRnaExport> exports = BeanConvertUtil.listConvert(list, CircRnaExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_Non-coding RNA_circRNA_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), CircRnaExport.class)
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
        reader.addHeaderAlias("Category", "categories");
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Sample", "sample");
        reader.addHeaderAlias("Dysfunction Pattern", "dysfunctionPattern");
        reader.addHeaderAlias("Validated Method", "validatedMethod");
        reader.addHeaderAlias("Description", "description");
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
        writer.addHeaderAlias("categories", "Category");
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("sample", "Sample");
        writer.addHeaderAlias("dysfunctionPattern", "Dysfunction Pattern");
        writer.addHeaderAlias("validatedMethod", "Validated Method");
        writer.addHeaderAlias("description", "Description");
        writer.addHeaderAlias("pmid", "PMID");
        writer.addHeaderAlias("pmidUrl", "pmidUrl");
        return writer;
    }

}

