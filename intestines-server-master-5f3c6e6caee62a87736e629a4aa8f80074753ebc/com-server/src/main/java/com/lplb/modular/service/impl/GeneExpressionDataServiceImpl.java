package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.enums.OmicsCategoryEnums;
import com.lplb.core.enums.OmicsEnums;
import com.lplb.core.enums.OrderByEnums;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.core.util.StringUtils;
import com.lplb.modular.mapper.GeneExpressionDataMapper;
import com.lplb.modular.model.entity.GeneExpressionData;
import com.lplb.modular.model.query.GeneExpressionDataQuery;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import com.lplb.modular.service.GeneExpressionDataService;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.GeoInformationService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：Gene expression data（基因表达数据）(GeneExpressionData)表服务实现类
 */
@Service
@Transactional
public class GeneExpressionDataServiceImpl extends ServiceImpl<GeneExpressionDataMapper, GeneExpressionData> implements GeneExpressionDataService {

    private final String tableName = "in_gene_expression_data";
    private final String filePath = "/03.Transcriptomic/Gene expression data";

    @Value("${file.requestPath}")
    private String fileRequestPath;

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private GeoInformationService geoInformationService;

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
            ZipSecureFile.setMinInflateRatio(0.001);
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 设置表头
            reader = this.readHeaderAlias(reader);

            // 获取列表
            List<GeneExpressionData> list = reader.readAll(GeneExpressionData.class);

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
                        item.setCategoryId(OmicsCategoryEnums.GENE_EXPRESSION_DATA.getId());
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到基因，统计放入数据库
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                GeneExpressionData.class.getName(),
//                                tableName,
//                                filePath,
//                                file.getOriginalFilename(),
//                                item.getId());

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
    public IPage<GeneExpressionDataVo> pageList(GeneExpressionDataQuery query) {

        // 分页
        Page<GeneExpressionData> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<GeneExpressionData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotEmpty(query.getProject()), GeneExpressionData::getProject, query.getProject());
        wrapper.eq(ObjectUtils.isNotEmpty(query.getSample()), GeneExpressionData::getBodySite, query.getSample());
        if (ObjectUtils.isNotEmpty(query.getSearch())) {
            wrapper.like(GeneExpressionData::getGeneName, query.getSearch())
                    .or()
                    .like(GeneExpressionData::getEnsembleId, query.getSearch());
        }

        // 排序
        if (ObjectUtils.isNotEmpty(query.getOrderBy())) {
            String orderBy = query.getOrderBy();
            String[] order = orderBy.split("_");
            String colum;
            if ("log2FoldChange".equals(order[0])) {
                colum = "log_2_fold_change";
            } else {
                colum = StringUtils.camelToUnderline(order[0]);
            }

            if (OrderByEnums.ASC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " ASC");
            } else if (OrderByEnums.DESC.getName().equals(order[1])) {
                wrapper.last("order by " + colum + " DESC");
            }
        } else {
            wrapper.orderByAsc(GeneExpressionData::getGeneName);
        }
        page = this.page(page, wrapper);

        // 转换
        IPage<GeneExpressionDataVo> result = BeanConvertUtil.pageConvert(page, GeneExpressionDataVo.class);
        result.getRecords().forEach(item -> {
            // 设置图片访问地址
            String boxplotImgUrl = fileRequestPath + item.getFilePath() + "/boxplot_img/" + item.getGeneName() + ".png";
            String dotplotImgUrl = fileRequestPath + item.getFilePath() + "/dotplot_img/" + item.getGeneName() + ".png";

            // A图
            String tpmExpImg = fileRequestPath + "imgs/IntestineDB/03.Transcriptomic/Gene_expression_data/Homo_sapiens/Barplot/A_RNA_cleaned_log_TPM/A_imgs/"
                    + item.getGeneName() + ".png";

            // D图
            String tsScoreImg = fileRequestPath + "imgs/IntestineDB/03.Transcriptomic/Gene_expression_data/Homo_sapiens/Barplot/D_RNA_TS_score/D_imgs/"
                    + item.getGeneName() + ".png";

            item.setBoxplotImgUrl(boxplotImgUrl);
            item.setDotplotImgUrl(dotplotImgUrl);
            item.setTpmExpImg(tpmExpImg);
            item.setTsScoreImg(tsScoreImg);

        });

        return result;
    }

    /**
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<GeneExpressionDataVo> listByGeneName(String geneName) {
        LambdaQueryWrapper<GeneExpressionData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GeneExpressionData::getGeneName, geneName);
        List<GeneExpressionData> list = this.list(wrapper);

        List<GeneExpressionDataVo> result = BeanConvertUtil.listConvert(list, GeneExpressionDataVo.class);
        result.forEach(item -> {
            // 设置图片访问地址
            String boxplotImgUrl = fileRequestPath + item.getFilePath() + "/boxplot_img/" + item.getGeneName() + ".png";
            String dotplotImgUrl = fileRequestPath + item.getFilePath() + "/dotplot_img/" + item.getGeneName() + ".png";
            item.setBoxplotImgUrl(boxplotImgUrl);
            item.setDotplotImgUrl(dotplotImgUrl);
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
        reader.addHeaderAlias("Ensemble ID", "ensembleId");
        reader.addHeaderAlias("Mean (Case)", "meanCase");
        reader.addHeaderAlias("Mean (Control)", "meanControl");
        reader.addHeaderAlias("Log2(Fold Change)", "log2FoldChange");
        reader.addHeaderAlias("p.value", "pValue");

        reader.addHeaderAlias("expType", "expType");
        reader.addHeaderAlias("project", "project");
        reader.addHeaderAlias("bodySite", "bodySite");
        reader.addHeaderAlias("filePath", "filePath");
        return reader;
    }
}

