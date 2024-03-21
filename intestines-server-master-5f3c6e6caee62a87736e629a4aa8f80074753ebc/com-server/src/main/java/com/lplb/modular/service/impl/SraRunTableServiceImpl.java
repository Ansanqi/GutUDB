package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
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
import com.lplb.modular.mapper.SraRunTableMapper;
import com.lplb.modular.model.entity.SraRunTable;
import com.lplb.modular.model.entity.SraRunTableGroupNotes;
import com.lplb.modular.model.entity.SraRunTableGroupRowsCells;
import com.lplb.modular.model.export.SraRunTableExport;
import com.lplb.modular.model.export.SraRunTableGroupNotesExport;
import com.lplb.modular.model.query.SraRunTableQuery;
import com.lplb.modular.model.vo.SraRunTableGroupRowsVo;
import com.lplb.modular.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Sheet;
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

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:45
 * @Description（描述）：SraRunTable（Sra运行表）(SraRunTable)表服务实现类
 */
@Service
@Transactional
public class SraRunTableServiceImpl extends ServiceImpl<SraRunTableMapper, SraRunTable> implements SraRunTableService {

    private final String tableName = "in_sra_run_table";
    private final String filePath = "/03.Transcriptomic/Alternative Splicing";
    private final String fileName = "SraRunTable";

    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private SraRunTableGroupRowsService sraRunTableGroupRowsService;
    @Resource
    private SraRunTableGroupRowsCellsService sraRunTableGroupRowsCellsService;
    @Resource
    private SraRunTableGroupNotesService sraRunTableGroupNotesService;

    /**
     * Excel导入
     *
     * @param file
     * @return
     */
    @Override
    public Boolean importExcel(MultipartFile file) {
        // 读取SraRunTable的sheet数据
        String dataAccessId = this.readSraRunTableData(file);

        // 读取分组数据
        this.readGroupData(file, dataAccessId);

        // 读取Note数据
        this.readNotes(file, dataAccessId);
        return true;
    }

    /**
     * 数据列表查询
     *
     * @param query
     * @return
     */
    @Override
    public IPage<SraRunTable> pageList(SraRunTableQuery query) {
        // 分页
        Page<SraRunTable> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        // 查询条件
        LambdaQueryWrapper<SraRunTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SraRunTable::getDataAccessId, query.getDataAccessId());
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(SraRunTable::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getCelType())) {
            wrapper.eq(SraRunTable::getCelType, query.getCelType());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SraRunTable::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getStrain())) {
            wrapper.eq(SraRunTable::getStrain, query.getStrain());
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
     * 分组信息查询
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public List<Map<String, Object>> groupInfos(String dataAccessId) {
        List<SraRunTableGroupRowsVo> list = sraRunTableGroupRowsService.groupInfosByDataAccessId(dataAccessId);

//        // 返回数据重新封装
//        List<Map<String, String>> result = new ArrayList<>();
//        list.forEach(item -> {
//            Map<String, String> map = new LinkedHashMap<>();
//            map.put("name", )
//        });
        List<Map<String, Object>> groupsExport = this.getGroupsExportData(list);
        return groupsExport;
    }

    /**
     * Excel导出
     *
     * @param response
     * @param query
     */
    @Override
    public void exportExcel(HttpServletResponse response, SraRunTableQuery query) {
        // 查询SraRunTable数据集合
        List<SraRunTable> list = this.listByByQuery(query);
        // 导出数据集合
        List<SraRunTableExport> exports = BeanConvertUtil.listConvert(list, SraRunTableExport.class);

        // 查询group表数据
        List<SraRunTableGroupRowsVo> groups = sraRunTableGroupRowsService.groupInfosByDataAccessId(query.getDataAccessId());

        // 查询groupNotes表数据
        List<SraRunTableGroupNotes> sraRunTableGroupNotes = sraRunTableGroupNotesService.findByDataAccessId(query.getDataAccessId());
        List<SraRunTableGroupNotesExport> notesExports = BeanConvertUtil.listConvert(sraRunTableGroupNotes, SraRunTableGroupNotesExport.class);
        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            // 文件名
            String name = "Omics_Transcriptomics_Alternative Splicing_Data Access_SraRun_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");

            com.alibaba.excel.ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).excelType(ExcelTypeEnum.CSV).build();
            // 写入第一个
            WriteSheet writeSheet = EasyExcel.writerSheet("SraRunTable").head(SraRunTableExport.class).build();
            // 添加4行默认空数据
            exports.add(new SraRunTableExport());
            exports.add(new SraRunTableExport());
            exports.add(new SraRunTableExport());
            exports.add(new SraRunTableExport());
            excelWriter.write(exports, writeSheet);

            // 写入第二个
            // 组装分组导出数据
            List<Map<String, Object>> groupsExport = this.getGroupsExportData(groups);
            WriteSheet writeSheet1 = EasyExcel.writerSheet("分组").head(groupsHead(groupsExport)).build();
            excelWriter.write(groupsData(groupsExport), writeSheet1);

            // 写入第三个
            WriteSheet writeSheet2 = EasyExcel.writerSheet("Note").head(SraRunTableGroupNotesExport.class).build();
            excelWriter.write(notesExports, writeSheet2);

            excelWriter.finish();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 分组数据处理
     * @param groupsExport
     * @return
     */
    private Collection<?> groupsData(List<Map<String, Object>> groupsExport) {
        List<List<Object>> datas = ListUtils.newArrayList();
        if (ObjectUtils.isNotEmpty(groupsExport)) {
            groupsExport.forEach(item -> {
                List<Object> data = ListUtils.newArrayList();
                item.forEach((key, value) -> {
                    data.add(value);
                });
                datas.add(data);
            });

            // 默认添加4行空数据
            for (int i = 0; i < 4; i++) {
                List<Object> data = ListUtils.newArrayList();
                data.add("");
                datas.add(data);
            }
        }
        return datas;
    }

    /**
     * 分组数据表头处理
     *
     * @param groupsExport
     * @return
     */
    private List<List<String>> groupsHead(List<Map<String, Object>> groupsExport) {
        List<List<String>> heads = ListUtils.newArrayList();
        if (ObjectUtils.isNotEmpty(groupsExport)) {
            Map<String, Object> map = groupsExport.get(0);
            map.forEach((key, value) -> {
                List<String> head = ListUtils.newArrayList();
                head.add(key);
                heads.add(head);
            });
        }
        return heads;
    }

    /**
     * 查询
     *
     * @param query
     * @return
     */
    private List<SraRunTable> listByByQuery(SraRunTableQuery query) {
        LambdaQueryWrapper<SraRunTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SraRunTable::getDataAccessId, query.getDataAccessId());
        if (ObjectUtils.isNotEmpty(query.getDisease())) {
            wrapper.eq(SraRunTable::getDisease, query.getDisease());
        }
        if (ObjectUtils.isNotEmpty(query.getCelType())) {
            wrapper.eq(SraRunTable::getCelType, query.getCelType());
        }
        if (ObjectUtils.isNotEmpty(query.getTissue())) {
            wrapper.eq(SraRunTable::getTissue, query.getTissue());
        }
        if (ObjectUtils.isNotEmpty(query.getStrain())) {
            wrapper.eq(SraRunTable::getStrain, query.getStrain());
        }

        // 查询数据集合
        List<SraRunTable> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<SraRunTable> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }
        return list;
    }

    /**
     * 根据dataAccessId查询数据列表
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public List<SraRunTable> listByByDataAccessId(String dataAccessId) {
        LambdaQueryWrapper<SraRunTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SraRunTable::getDataAccessId, dataAccessId);
        return this.list(wrapper);
    }

    /**
     * 分组Note信息查询
     *
     * @param dataAccessId
     * @return
     */
    @Override
    public String groupNotes(String dataAccessId) {
        List<SraRunTableGroupNotes> list = sraRunTableGroupNotesService.findByDataAccessId(dataAccessId);
        if (ObjectUtils.isNotEmpty(list)) {
            return list.get(0).getNotes();
        }
        return "";
    }

    /**
     * 读取SraRunTable的sheet数据
     *
     * @param file
     * @return
     */
    private String readSraRunTableData(MultipartFile file) {
        InputStream inputStream = null;
        try {
            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream, 0);

            // 设置表头
            reader = this.readSraRunTableHeaderAlias(reader);

            // 获取列表
            List<SraRunTable> list = reader.readAll(SraRunTable.class);

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
//                        // 上级文件编号
//                        item.setDataAccessId(dataAccessId);

                        flag.set(save(item));

                        // TODO 统计数据无用，暂时注释
//                        // 该数据关系到疾病，统计放入数据库
//                        diseaseService.savaDiseaseAndSeq(item.getDisease(),
//                                item.getDisease(),
//                                OmicsEnums.TRANSCRIPTOMIC.getId(),
//                                SraRunTable.class.getName(),
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
            if (ObjectUtils.isNotEmpty(list)) {
                return list.get(0).getDataAccessId();
            }
            return "";
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
     * 读取分组sheet数据
     *
     * @param file
     * @param dataAccessId
     */
    private Boolean readGroupData(MultipartFile file, String dataAccessId) {
        InputStream inputStream = null;
        try {
            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream, 1);
            // 读取结果
            List<Map<String, Object>> data = reader.readAll();

            // 将结果重新保存，方便存储
            Map<String, List<Object>> saveResult = new LinkedHashMap<>();
            data.forEach(item -> {
                item.forEach((key, value) -> {
                    List<Object> list;
                    if (saveResult.containsKey(key)) {
                        list = saveResult.get(key);
                    } else {
                        list = new ArrayList<>();
                    }
                    list.add(value);
                    saveResult.put(key, list);
                });
            });

            // 数据保存
            saveResult.forEach((key, value) -> {
                // 保存SraRunTable分组，先保存列名
                sraRunTableGroupRowsService.saveGroup(key, value, dataAccessId);
            });
            return true;
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
     * 读取Note数据
     *
     * @param file
     * @param dataAccessId
     */
    private void readNotes(MultipartFile file, String dataAccessId) {

        InputStream inputStream = null;
        try {
            // 文件读取
            inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream, 2);

            // 设置表头
            reader = this.readSraRunTableGroupNoteHeaderAlias(reader);

            // 获取列表
            List<SraRunTableGroupNotes> list = reader.readAll(SraRunTableGroupNotes.class);

            list.forEach(item -> {
                item.setDataAccessId(dataAccessId);
                sraRunTableGroupNotesService.save(item);
            });
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
     * 读取SraRunTable表头
     *
     * @param reader
     * @return
     */
    private ExcelReader readSraRunTableHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Disease", "disease");
        reader.addHeaderAlias("Disease_stage", "diseaseStage");
        reader.addHeaderAlias("Cell Type", "celType");
        reader.addHeaderAlias("Tissue", "tissue");
        reader.addHeaderAlias("Strain", "strain");
        reader.addHeaderAlias("Treatment", "treatment");
        reader.addHeaderAlias("Time", "time");
        reader.addHeaderAlias("Age", "age");
        reader.addHeaderAlias("Run", "run");
        reader.addHeaderAlias("Data Access ID", "dataAccessId");
        return reader;
    }

    /**
     * 读取SraRunTableGroupNote表头
     *
     * @param reader
     * @return
     */
    private ExcelReader readSraRunTableGroupNoteHeaderAlias(ExcelReader reader) {
        reader.addHeaderAlias("Note", "notes");
        return reader;
    }

    /**
     * 写入SraRunTable表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerSraRunTableHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("disease", "Disease");
        writer.addHeaderAlias("diseaseStage", "Disease_stage");
        writer.addHeaderAlias("celType", "Cell Type");
        writer.addHeaderAlias("tissue", "Tissue");
        writer.addHeaderAlias("strain", "Strain");
        writer.addHeaderAlias("treatment", "Treatment");
        writer.addHeaderAlias("time", "Time");
        writer.addHeaderAlias("age", "Age");
        writer.addHeaderAlias("run", "Run");
        writer.addHeaderAlias("dataAccessId", "Data Access ID");
        return writer;
    }

    /**
     * 设置SraRunTableGroupNotes表头
     *
     * @param writer
     * @return
     */
    private ExcelWriter writerSraRunTableGroupNotesHeaderAlias(ExcelWriter writer) {
        writer.addHeaderAlias("notes", "Note");
        writer.addHeaderAlias("dataAccessId", "Data Access ID");
        return writer;
    }

    /**
     * 设置分组表头
     *
     * @param groups
     * @return
     */
    private ExcelWriter writerGroupHeaderAlias(ExcelWriter writer, List<SraRunTableGroupRowsVo> groups) {
        groups.forEach(item -> {
            writer.addHeaderAlias(item.getName(), item.getName());
        });
        return writer;
    }

    /**
     * 组装分组导出数据
     *
     * @param groups
     * @return
     */
    private List<Map<String, Object>> getGroupsExportData(List<SraRunTableGroupRowsVo> groups) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 获取分组中最大group中cell个数
        AtomicInteger cellCount = new AtomicInteger(1);
        groups.forEach(item -> {
            cellCount.set(item.getCells().size() > cellCount.get() ? item.getCells().size() : cellCount.get());
        });

        // 循环
        for (int i = 0; i < cellCount.get(); i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (SraRunTableGroupRowsVo item : groups) {
                String key = item.getName();
                SraRunTableGroupRowsCells cell;
                if (item.getCells().size() > i) {
                    cell = item.getCells().get(i);
                } else {
                    cell = null;
                }
                if (ObjectUtils.isNotEmpty(cell)) {
                    map.put(key, cell.getName());
                } else {
                    map.put(key, "");
                }
            }
            result.add(map);
        }
        return result;
    }
}

