package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.exception.ServiceException;
import com.lplb.core.util.BeanConvertUtil;
import com.lplb.modular.mapper.TcgaCoadExpMapper;
import com.lplb.modular.model.entity.TcgaCoadExp;
import com.lplb.modular.model.vo.GeneExpressionDataVo;
import com.lplb.modular.service.GeneService;
import com.lplb.modular.service.TcgaCoadExpService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
 * @Date（日期）： 2023-08-16 19:08:44
 * @Description（描述）：TCGA COAD_exp(TcgaCoadExp)表服务实现类
 */
@Service
public class TcgaCoadExpServiceImpl extends ServiceImpl<TcgaCoadExpMapper, TcgaCoadExp> implements TcgaCoadExpService {

    private final String tableName = "in_tcga_coad_exp";
    private final String filePath = "/03.Transcriptomic/Gene expression data/Homo sapiens/Boxplot&Dotplot/TCGA_COAD";

    @Value("${file.requestPath}")
    private String fileRequestPath;
    @Resource
    private GeneService geneService;
    @Resource
    private ThreadPoolExecutor executor;

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
            List<TcgaCoadExp> list = reader.readAll(TcgaCoadExp.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

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
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public List<GeneExpressionDataVo> listByGeneName(String geneName) {
        LambdaQueryWrapper<TcgaCoadExp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcgaCoadExp::getGeneName, geneName);
        List<TcgaCoadExp> list = this.list(wrapper);

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
        reader.addHeaderAlias("p.value", "pValue");
        reader.addHeaderAlias("filePath", "filePath");
        return reader;
    }
}

