package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.exception.ServiceException;
import com.lplb.modular.mapper.RbpAndTfListMapper;
import com.lplb.modular.model.entity.RbpAndTfList;
import com.lplb.modular.service.RbpAndTfListService;
import org.apache.commons.lang3.ObjectUtils;
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
 * @Date（日期）： 2023-08-01 16:16:05
 * @Description（描述）：RBP and TF list表(RbpAndTfList)表服务实现类
 */
@Service
@Transactional
public class RbpAndTfListServiceImpl extends ServiceImpl<RbpAndTfListMapper, RbpAndTfList> implements RbpAndTfListService {

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
            List<RbpAndTfList> list = reader.readAll(RbpAndTfList.class);

            // 保存结果
            AtomicBoolean flag = new AtomicBoolean(true);
            // 出错行
            AtomicInteger errorLine = new AtomicInteger();

            list.forEach(item -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (save(item)) {
                            flag.set(save(item));
                            errorLine.set(list.indexOf(item));
                        }
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

    /***
     * 根据基因名称查询
     *
     * @param geneName
     * @return
     */
    @Override
    public RbpAndTfList getByGeneName(String geneName) {
        LambdaQueryWrapper<RbpAndTfList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RbpAndTfList::getGeneName, geneName);
        return this.getOne(wrapper);
    }

    /**
     * 首页搜索
     *
     * @param searchText
     * @return
     */
    @Override
    public List<RbpAndTfList> firstPageSearch(String searchText) {
        LambdaQueryWrapper<RbpAndTfList> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(RbpAndTfList::getGeneName, searchText);
        return this.list(wrapper);
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Ensemble ID", "ensembleId");
        reader.addHeaderAlias("Gene Name", "geneName");
        reader.addHeaderAlias("Seqnames", "seqnames");
        reader.addHeaderAlias("Start", "start");
        reader.addHeaderAlias("End", "end");
        reader.addHeaderAlias("Strand", "strand");
        reader.addHeaderAlias("RBP", "rbp");
        reader.addHeaderAlias("m6A", "m6a");
        reader.addHeaderAlias("AS", "asFlag");
        reader.addHeaderAlias("RNA edting", "rnaEdting");
        reader.addHeaderAlias("TF", "tf");
        reader.addHeaderAlias("Motif", "motif");
        reader.addHeaderAlias("Therapeutic targets", "therapeuticTargets");
        reader.addHeaderAlias("Drug", "drug");
        reader.addHeaderAlias("Profile", "profile");
        reader.addHeaderAlias("NCBI Association", "ncbiAssociation");
        reader.addHeaderAlias("Genomics", "genomics");
        reader.addHeaderAlias("Metabolomics", "metabolomics");
        reader.addHeaderAlias("Proteomics", "proteomics");
        reader.addHeaderAlias("Single cell and spatial omics data", "singleCellAndSpatialOmicsData");
        reader.addHeaderAlias("Transcriptomic", "transcriptomic");
        return reader;
    }
}

