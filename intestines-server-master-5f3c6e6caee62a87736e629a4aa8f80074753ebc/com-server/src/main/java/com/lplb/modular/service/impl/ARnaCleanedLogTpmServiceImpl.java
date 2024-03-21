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
import com.lplb.modular.mapper.ARnaCleanedLogTpmMapper;
import com.lplb.modular.model.entity.ARnaCleanedLogTpm;
import com.lplb.modular.model.entity.GeneExpressionData;
import com.lplb.modular.model.query.ARnaCleanedLogTpmQuery;
import com.lplb.modular.model.vo.ARnaCleanedLogTpmVo;
import com.lplb.modular.service.ARnaCleanedLogTpmService;
import com.lplb.modular.service.GeneExpressionDataService;
import com.lplb.modular.service.GeneService;
import org.apache.commons.lang3.ObjectUtils;
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
import java.util.stream.Collectors;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:28
 * @Description（描述）：A RNA cleaned log TPM(ARnaCleanedLogTpm)表服务实现类
 */
@Service
@Transactional
public class ARnaCleanedLogTpmServiceImpl extends ServiceImpl<ARnaCleanedLogTpmMapper, ARnaCleanedLogTpm> implements ARnaCleanedLogTpmService {

    private final String tableName = "in_a_rna_cleaned_log_tpm";
    private final String filePath = "/03.Transcriptomic/Gene expression data/Homo sapiens/Barplot/A RNA cleaned log TPM";
    private final String fileName = "A_RNA_cleaned_log_TPM";

    @Value("${file.requestPath}")
    private String fileRequestPath;

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private GeneService geneService;
    @Resource
    private GeneExpressionDataService geneExpressionDataService;

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
            List<ARnaCleanedLogTpm> list = reader.readAll(ARnaCleanedLogTpm.class);

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
//                        // 保存基因
//                        geneService.saveGeneAndSeq(item.getGeneName(),
//                                item.getGeneName(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                ARnaCleanedLogTpm.class.getName(),
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
    public IPage<ARnaCleanedLogTpmVo> pageList(ARnaCleanedLogTpmQuery query) {
        // 分页
        Page<ARnaCleanedLogTpm> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询in_gene_expression_data表，获取所有基因
        LambdaQueryWrapper<GeneExpressionData> dataWrapper = new LambdaQueryWrapper<>();
        dataWrapper.select(GeneExpressionData::getGeneName);
        dataWrapper.eq(GeneExpressionData::getBodySite, query.getSample());
        dataWrapper.eq(GeneExpressionData::getProject, query.getProject());
        dataWrapper.groupBy(GeneExpressionData::getGeneName);
        dataWrapper.orderByAsc(GeneExpressionData::getGeneName);
        List<GeneExpressionData> dataList = geneExpressionDataService.list(dataWrapper);

        // 基因列表
        List<String> genes = null;
        if (ObjectUtils.isNotEmpty(dataList) && ObjectUtils.isNotEmpty(dataList.get(0)) && ObjectUtils.isNotEmpty(dataList.get(0).getGeneName())) {
            genes = dataList.stream().map(item -> item.getGeneName()).collect(Collectors.toList());
        }

        // 查询条件
        LambdaQueryWrapper<ARnaCleanedLogTpm> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(genes)) {
            wrapper.in(ARnaCleanedLogTpm::getGeneName, genes);
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
        } else {
            wrapper.orderByAsc(ARnaCleanedLogTpm::getGeneName);
        }
        page = this.page(page, wrapper);

        IPage<ARnaCleanedLogTpmVo> result = BeanConvertUtil.pageConvert(page, ARnaCleanedLogTpmVo.class);
        result.getRecords().forEach(item -> {
            // 设置图片访问地址
            String imgUrl = fileRequestPath + "imgs/IntestineDB/03.Transcriptomic/Gene_expression_data/Homo_sapiens/Barplot/A_RNA_cleaned_log_TPM/A_imgs/"
                    + item.getGeneName() + ".png";
            item.setImgUrl(imgUrl);
        });
        return result;
    }

    /**
     * 通过基因名称查询列表
     *
     * @param geneName
     * @return
     */
    @Override
    public List<ARnaCleanedLogTpmVo> listByGeneName(String geneName) {
        LambdaQueryWrapper<ARnaCleanedLogTpm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ARnaCleanedLogTpm::getGeneName, geneName);
        List<ARnaCleanedLogTpm> list = this.list(wrapper);

        List<ARnaCleanedLogTpmVo> result = BeanConvertUtil.listConvert(list, ARnaCleanedLogTpmVo.class);

        // 查询图片地址
        result.forEach(item -> {
            // 设置图片访问地址
            String imgUrl = fileRequestPath + "imgs/IntestineDB/03.Transcriptomic/Gene_expression_data/Homo_sapiens/Barplot/A_RNA_cleaned_log_TPM/A_imgs/"
                    + item.getGeneName() + ".png";
            item.setImgUrl(imgUrl);
        });
        return result;
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("gene_name", "geneName");
        reader.addHeaderAlias("Adrenal_Gland", "adrenalGland");
        reader.addHeaderAlias("Spleen", "spleen");
        reader.addHeaderAlias("Heart_Atrial_Appendage", "heartAtrialAppendage");
        reader.addHeaderAlias("Pancreas", "pancreas");
        reader.addHeaderAlias("Artery_Aorta", "arteryAorta");
        reader.addHeaderAlias("Breast_Mammary_Tissue", "breastMammaryTissue");
        reader.addHeaderAlias("Small_Intestine_Terminal_Ileum", "smallIntestineTerminalIleum");
        reader.addHeaderAlias("Colon_Sigmoid", "colonSigmoid");
        reader.addHeaderAlias("Esophagus_Mucosa", "esophagusMucosa");
        reader.addHeaderAlias("Esophagus_Muscularis", "esophagusMuscularis");
        reader.addHeaderAlias("Esophagus_Gastroesophageal_Junction", "esophagusGastroesophagealJunction");
        reader.addHeaderAlias("Muscle_Skeletal", "muscleSkeletal");
        reader.addHeaderAlias("Thyroid", "thyroid");
        reader.addHeaderAlias("Heart_Left_Ventricle", "heartLeftVentricle");
        reader.addHeaderAlias("Artery_Coronary", "arteryCoronary");
        reader.addHeaderAlias("Stomach", "stomach");
        reader.addHeaderAlias("Uterus", "uterus");
        reader.addHeaderAlias("Vagina", "vagina");
        reader.addHeaderAlias("Colon_Transverse", "colonTransverse");
        reader.addHeaderAlias("Skin_Not_Sun_Exposed(Suprapubic)", "skinNotSunExposedSuprapubic");
        reader.addHeaderAlias("Nerve_Tibial", "nerveTibial");
        reader.addHeaderAlias("Artery_Tibial", "arteryTibial");
        reader.addHeaderAlias("Ovary", "ovary");
        reader.addHeaderAlias("Liver", "liver");
        reader.addHeaderAlias("Lung", "lung");
        reader.addHeaderAlias("Brain_Cerebellum", "brainCerebellum");
        reader.addHeaderAlias("Brain_Cortex", "brainCortex");
        reader.addHeaderAlias("Skin_Sun_Exposed(Lower_leg)", "skinSunExposedLowerLeg");
        reader.addHeaderAlias("Pituitary", "pituitary");
        reader.addHeaderAlias("Testis", "testis");
        reader.addHeaderAlias("Prostate", "prostate");
        reader.addHeaderAlias("Minor_Salivary_Gland", "minorSalivaryGland");
        return reader;
    }
}

