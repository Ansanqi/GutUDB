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
import com.lplb.modular.mapper.LncRnaMapper;
import com.lplb.modular.model.entity.CircRna;
import com.lplb.modular.model.entity.LncRna;
import com.lplb.modular.model.export.CircRnaExport;
import com.lplb.modular.model.export.LncRnaExport;
import com.lplb.modular.model.query.LncRnaQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.service.DiseaseService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.LncRnaService;
import com.lplb.modular.service.PmDataService;
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
 * @Date（日期）： 2023-07-27 18:35:36
 * @Description（描述）：lnc RNA（长非编码核糖核酸）(LncRna)表服务实现类
 */
@Service
@Transactional
public class LncRnaServiceImpl extends ServiceImpl<LncRnaMapper, LncRna> implements LncRnaService {

    private final String tableName = "in_lnc_rna";
    private final String filePath = "/03.Transcriptomic/Non-coding RNA";
    private final String fileName = "lncRNA";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
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
            List<LncRna> list = reader.readAll(LncRna.class);

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

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                CircRna.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到疾病，统计放入数据库
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                CircRna.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );
//
//                        // 该数据包含PMID数据，需要保存到对应数据库
//                        pmDataService.savePmData(OmicsEnums.GENOMICS.getId(),
//                                OmicsCategoryEnums.CNA_GENES.getId(),
//                                item.getId(),
//                                PmDataType.CNA_GENES.getName(),
//                                item.getPmid(),
//                                PmDataConstant.PMID_BASE_URL + item.getPmid());

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
    public IPage<LncRna> pageList(LncRnaQuery query) {
        // 分页
        Page<LncRna> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<LncRna> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(LncRna::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(LncRna::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(LncRna::getSpecies, query.getSpecies());
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
    public List<LncRna> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<LncRna> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LncRna::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(LncRna::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Top 10 lnc RNA
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10LncRna() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10LncRna();
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
    public void exportExcel(HttpServletResponse response, LncRnaQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<LncRna> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(LncRna::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(LncRna::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getSpecies())) {
            wrapper.eq(LncRna::getSpecies, query.getSpecies());
        }

        // 查询数据集合
        List<LncRna> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<LncRna> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<LncRnaExport> exports = BeanConvertUtil.listConvert(list, LncRnaExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_Non-coding RNA_lnc RNA_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), LncRnaExport.class)
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
        reader.addHeaderAlias("Ensemble ID", "ensembleId");
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Species", "species");
        reader.addHeaderAlias("Function", "function");
        reader.addHeaderAlias("Description", "description");
        reader.addHeaderAlias("Chr", "chr");
        reader.addHeaderAlias("Start", "start");
        reader.addHeaderAlias("End", "end");
        reader.addHeaderAlias("Strand", "strand");
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
        writer.addHeaderAlias("ensembleId", "Ensemble ID");
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("species", "Species");
        writer.addHeaderAlias("function", "Function");
        writer.addHeaderAlias("description", "Description");
        writer.addHeaderAlias("chr", "Chr");
        writer.addHeaderAlias("start", "Start");
        writer.addHeaderAlias("end", "End");
        writer.addHeaderAlias("strand", "Strand");
        writer.addHeaderAlias("pmid", "PMID");
        writer.addHeaderAlias("pmidUrl", "pmidUrl");
        return writer;
    }
}

