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
import com.lplb.modular.mapper.GeoInfoMapper;
import com.lplb.modular.model.entity.GeoInfo;
import com.lplb.modular.model.export.GeoInfoExport;
import com.lplb.modular.model.query.GeoInfoQuery;
import com.lplb.modular.service.GeoInfoService;
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
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:31
 * @Description（描述）：GEO_info（高通量基因表达信息）(GeoInfo)表服务实现类
 */
@Service
@Transactional
public class GeoInfoServiceImpl extends ServiceImpl<GeoInfoMapper, GeoInfo> implements GeoInfoService {

    private final String tableName = "in_geo_info";
    private final String filePath = "/03.Transcriptomic/Alternative Splicing";
    private final String fileName = "GEO_information";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private TissueCellLineService tissueCellLineService;

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
            List<GeoInfo> list = reader.readAll(GeoInfo.class);

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
                        item.setCategoryId(OmicsCategoryEnums.ALTERNATIVE_SPLICING.getId());
                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到组织细胞系，统计放入数据库
//                        tissueCellLineService.savaTissueAndSeq(
//                                item.getTissue(),
//                                item.getTissue(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                GeoInfo.class.getName(),
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
    public IPage<GeoInfo> pageList(GeoInfoQuery query) {
        // 分页
        Page<GeoInfo> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<GeoInfo> wrapper = new LambdaQueryWrapper<>();

        if (ObjectUtils.isNotEmpty(query.getDataAccessId())) {
            String dataAccessId = query.getDataAccessId();
            if (!dataAccessId.contains("GSE")) {
                dataAccessId = "GSE" + dataAccessId;
            }
            wrapper.eq(GeoInfo::getDataAccessId, dataAccessId);
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(GeoInfo::getConditions, query.getConditions());
        }
        if (ObjectUtils.isNotEmpty(query.getCellLineTissueOrganiod())) {
            wrapper.eq(GeoInfo::getCellLineTissueOrganiod, query.getCellLineTissueOrganiod());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(GeoInfo::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getTypeCategory())) {
            wrapper.eq(GeoInfo::getTypeCategory, query.getTypeCategory());
        }
        if (ObjectUtils.isNotEmpty(query.getCellType())) {
            wrapper.eq(GeoInfo::getCellType, query.getCellType());
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
     * Excel导出
     *
     * @param response
     */
    @Override
    public void exportExcel(HttpServletResponse response, GeoInfoQuery query) {
        // 查询条件
        LambdaQueryWrapper<GeoInfo> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getDataAccessId())) {
            String dataAccessId = query.getDataAccessId();
            if (!dataAccessId.contains("GSE")) {
                dataAccessId = "GSE" + dataAccessId;
            }
            wrapper.eq(GeoInfo::getDataAccessId, dataAccessId);
        }
        if (ObjectUtils.isNotEmpty(query.getConditions())) {
            wrapper.eq(GeoInfo::getConditions, query.getConditions());
        }
        if (ObjectUtils.isNotEmpty(query.getCellLineTissueOrganiod())) {
            wrapper.eq(GeoInfo::getCellLineTissueOrganiod, query.getCellLineTissueOrganiod());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(GeoInfo::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getTypeCategory())) {
            wrapper.eq(GeoInfo::getTypeCategory, query.getTypeCategory());
        }
        if (ObjectUtils.isNotEmpty(query.getCellType())) {
            wrapper.eq(GeoInfo::getCellType, query.getCellType());
        }

        // 查询数据集合
        List<GeoInfo> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<GeoInfo> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 导出数据集合
        List<GeoInfoExport> exports = BeanConvertUtil.listConvert(list, GeoInfoExport.class);

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Transcriptomics_Alternative Splicing_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream(), GeoInfoExport.class)
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
    public List<GeoInfo> listByGeneName(String geneName) {
        return null;
    }

    /**
     * 读取表头
     *
     * @return
     */
    private ExcelReader readHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Data Access ID", "dataAccessId");
        reader.addHeaderAlias("Group", "groups");
        reader.addHeaderAlias("Information", "information");
        reader.addHeaderAlias("Condition", "conditions");
        reader.addHeaderAlias("Cell line/Tissue/Organiod", "cellLineTissueOrganiod");
        reader.addHeaderAlias("Tissue", "tissue");
        reader.addHeaderAlias("Type Category", "typeCategory");
        reader.addHeaderAlias("Cell Type", "cellType");
        reader.addHeaderAlias("Body Site", "bodyTite");
        reader.addHeaderAlias("File", "fileNo");
        reader.addHeaderAlias("Run", "run");
        return reader;
    }

    /**
     * 写入表头
     *
     * @return
     */
    private ExcelWriter writerHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("dataAccessId", "Data Access ID");
        writer.addHeaderAlias("groups", "Group");
        writer.addHeaderAlias("information", "Information");
        writer.addHeaderAlias("conditions", "Condition");
        writer.addHeaderAlias("cellLineTissueOrganiod", "Cell line/Tissue/Organiod");
        writer.addHeaderAlias("tissue", "Tissue");
        writer.addHeaderAlias("typeCategory", "Type Category");
        writer.addHeaderAlias("cellType", "Cell Type");
        writer.addHeaderAlias("bodyTite", "Body Site");
        writer.addHeaderAlias("fileNo", "File");
        writer.addHeaderAlias("run", "Run");
        return writer;
    }

}

