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
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.StructuraVariantGenesMapper;
import com.lplb.modular.model.entity.StructuraVariantGenes;
import com.lplb.modular.model.export.CnaGenesExport;
import com.lplb.modular.model.export.StructuraVariantGenesExport;
import com.lplb.modular.model.query.StructuraVariantGenesQuery;
import com.lplb.modular.model.vo.StatisticsVo;
import com.lplb.modular.model.vo.StructuraVariantGenesVo;
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
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:46
 * @Description（描述）：Structural Variant Genes（结构变异基因）(StructuraVariantGenes)表服务实现类
 */
@Service
@Transactional
public class StructuraVariantGenesServiceImpl extends ServiceImpl<StructuraVariantGenesMapper, StructuraVariantGenes> implements StructuraVariantGenesService {

    private final String tableName = "in_structura_variant_genes";
    private final String filePath = "/02.Genomics/Genomic alteration";
    private final String fileName = "Structural_Variant_Genes";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private PmDataService pmDataService;
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
            List<StructuraVariantGenes> list = reader.readAll(StructuraVariantGenes.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置组学ID
                        item.setOmicsId(OmicsEnums.GENOMICS.getId());
                        // 设置分类ID
                        item.setCategoryId(OmicsCategoryEnums.STRUCTURAL_VARIANT_GENES.getId());
                        // pmid处理
                        if (ObjectUtils.isNotEmpty(item.getPmid())) {
                            // 去掉多余的[uid]部分
                            String pmidStr = item.getPmid().replace("[uid]", "");
                            List<String> pmids = Arrays.asList(pmidStr.split(","));
                            List<String> pmidUrls = new ArrayList<>();
                            pmids.forEach(pmid -> {
                                pmidUrls.add(PmDataConstant.PMID_BASE_URL + pmid);
                            });
                            String pmidUrl = pmidUrls.stream().collect(Collectors.joining(","));
                            item.setPmid(pmidStr);
                            item.setPmidUrl(pmidUrl);
                        }

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.GENOMICS.getId(),
//                                Snp.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());
//
//                        // 该数据关系到疾病，统计放入数据库
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.GENOMICS.getId(),
//                                Snp.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId()
//                        );
//
//                        // 该数据包含PMID数据，需要保存到对应数据库
//                        if (ObjectUtils.isNotEmpty(item.getPmid())) {
//                            // 去掉多余的[uid]部分
//                            String pmidStr = item.getPmid().replace("[uid]", "");
//                            List<String> pmids = Arrays.asList(pmidStr.split(","));
//                            pmids.forEach(pmid -> {
//                                pmDataService.savePmData(OmicsEnums.GENOMICS.getId(),
//                                        OmicsCategoryEnums.STRUCTURAL_VARIANT_GENES.getId(),
//                                        item.getId(),
//                                        PmDataType.STRUCTURAL_VARIANT_GENES.getName(),
//                                        pmid,
//                                        PmDataConstant.PMID_BASE_URL + pmid);
//                            });
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
    public IPage<StructuraVariantGenesVo> pageList(StructuraVariantGenesQuery query) {
        // 分页
        Page<StructuraVariantGenes> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<StructuraVariantGenes> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(StructuraVariantGenes::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(StructuraVariantGenes::getDisease, query.getDisease());
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

        IPage<StructuraVariantGenesVo> result = BeanConvertUtil.pageConvert(page, StructuraVariantGenesVo.class);
        result.getRecords().forEach(item -> {
            // 查询颜色
            String color = filedColorService.findByType("in_structura_variant_genes", "freq", item.getFreq());
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
    public void exportExcel(HttpServletResponse response, StructuraVariantGenesQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<StructuraVariantGenes> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(StructuraVariantGenes::getGeneName, query.getKeywords());
        }
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(StructuraVariantGenes::getDisease, query.getDisease());
        }

        // 查询数据集合
        List<StructuraVariantGenes> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<StructuraVariantGenes> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<StructuraVariantGenesExport> exports = BeanConvertUtil.listConvert(list, StructuraVariantGenesExport.class);
        // PMID处理，全面添加一个'，否则，excel会默认未数字
        exports.forEach(item -> {
            item.setPmid("'" + item.getPmid());
        });

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Genomics_Genomic Alteration_Structural Variant Genes_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), StructuraVariantGenesExport.class)
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
     * @param disease
     * @return
     */
    @Override
    public List<StructuraVariantGenes> listByGeneName(String geneName, String disease) {
        LambdaQueryWrapper<StructuraVariantGenes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StructuraVariantGenes::getGeneName, geneName);
        if (ObjectUtils.isNotEmpty(disease)) {
            wrapper.eq(StructuraVariantGenes::getDisease, disease);
        }
        return this.list(wrapper);
    }

    /**
     * Top 10 Structural Variant Genes
     *
     * @return
     */
    @Override
    public Map<String, Integer> top10StructuralVariantGenes() {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<StatisticsVo> list = this.baseMapper.top10StructuralVariantGenes();
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
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Is Cancer Gene (source: OncoKB)", "isCancerGeneSourceOncoKb");
        reader.addHeaderAlias("Freq", "freq");
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
        writer.addHeaderAlias("geneName", "Gene Name");
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("isCancerGeneSourceOncoKb", "Is Cancer Gene (source: OncoKB)");
        writer.addHeaderAlias("freq", "Freq");
        writer.addHeaderAlias("pmid", "PMID");
        return writer;
    }

}

