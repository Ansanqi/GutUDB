package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.core.consts.CommonConstant;
import com.lplb.core.exception.ServiceException;
import com.lplb.modular.mapper.MousecolonProteinMapper;
import com.lplb.modular.model.entity.MousecolonProtein;
import com.lplb.modular.model.query.MousecolonProteinQuery;
import com.lplb.modular.service.MousecolonProteinService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-09-11 09:42:06
 * @Description（描述）：GSM6578068_mousecolon_protein(MousecolonProtein)表服务实现类
 */
@Service
public class MousecolonProteinServiceImpl extends ServiceImpl<MousecolonProteinMapper, MousecolonProtein> implements MousecolonProteinService {

    private final String fileName = "GSM6578068_mousecolon_protein";

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

            // 获取列表（源码中Map的实现类使用的是LinkedHashMap，因此返回的字段和表中的字段是同顺序的，及key的顺序和Excel中的表头顺序一样）
            List<Map<String, Object>> list = reader.readAll();

            // 循环
            list.forEach(item -> {
                // cell_phenotypic_markers
                String cellPhenotypicMarkers = ObjectUtils.isEmpty(item.get("Cell phenotypic markers")) ? "NA" : String.valueOf(item.get("Cell phenotypic markers"));
                // 保存
                MousecolonProtein mousecolonProtein = new MousecolonProtein();
                mousecolonProtein.setCellPhenotypicMarkers(cellPhenotypicMarkers);
                mousecolonProtein.setOrderBy(list.indexOf(item) + 1);

                // ---------------------------------------------------------
                // 将map中的值，放入转换成json字符串放入数据中，但是要排除geneName字段
                JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
                item.forEach((key, value) -> {
                    if (!"Cell phenotypic markers".equals(key)) {
                        jsonObject.put(key, value);
                    }
                });
                String details = JSONObject.toJSONString(jsonObject);
                mousecolonProtein.setDetails(details);
                this.save(mousecolonProtein);
            });

            return false;
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
     * 列表获取
     *
     * @param query
     * @return
     */
    @Override
    public IPage<Map<String, Object>> pageList(MousecolonProteinQuery query) {
        // 分页查询基因列表
        Page<MousecolonProtein> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        LambdaQueryWrapper<MousecolonProtein> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(ObjectUtils.isNotEmpty(query.getKeywords()), MousecolonProtein::getCellPhenotypicMarkers, query.getKeywords());
        }

        wrapper.orderByAsc(MousecolonProtein::getOrderBy);
        page = this.page(page, wrapper);

        // 返回数据集合
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 循环查询对应基因的前13条详情数据
        page.getRecords().forEach(item -> {

            // 数据列表
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("cellPhenotypicMarkers", item.getCellPhenotypicMarkers());
            // 取出基因详情，并转化为LinkdHashMap
            LinkedHashMap<String, Object> details = JSONObject.parseObject(item.getDetails(), LinkedHashMap.class);
            // 取出详情放入返回结果中
            Iterator<Map.Entry<String, Object>> iterator = details.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                map.put("x-" + next.getKey(), next.getValue());
            }
            resultList.add(map);
        });

        IPage<Map<String, Object>> result = new Page<>();
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setRecords(resultList);

        return result;
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> details(Long id) {
        // 查询
        MousecolonProtein mousecolonProtein = this.getById(id);
        if (ObjectUtils.isEmpty(mousecolonProtein)) {
            return null;
        }

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("id", mousecolonProtein.getId());
        result.put("cellPhenotypicMarkers", mousecolonProtein.getCellPhenotypicMarkers());

        // 循环改键
        LinkedHashMap<String, Object> map = JSONObject.parseObject(mousecolonProtein.getDetails(), LinkedHashMap.class);
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            result.put("x-" + next.getKey(), next.getValue());
        }
        return result;
    }

    /**
     * 导出
     *
     * @param response
     */
    @Override
    public void exportExcel(HttpServletResponse response, MousecolonProteinQuery query) {
        // 查询数据集合
        // 查询条件
        LambdaQueryWrapper<MousecolonProtein> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getSearchText())) {
            wrapper.like(ObjectUtils.isNotEmpty(query.getKeywords()), MousecolonProtein::getCellPhenotypicMarkers, query.getKeywords());
        }

        // 查询数据集合
        List<MousecolonProtein> list;
        int count = this.count(wrapper);
        if (count > CommonConstant.MAX_EXPORT_COUNT) {
            Page<MousecolonProtein> page = new Page<>(query.getExportCurrent(), query.getExportSize());
            list = this.page(page, wrapper).getRecords();
        } else {
            list = this.list(wrapper);
        }

        // 重写数据结构
        List<Map<String, Object>> exportList = new ArrayList<>();
        list.forEach(item -> {
            Map<String, Object> main = new LinkedHashMap<>();
            main.put("Cell phenotypic markers", item.getCellPhenotypicMarkers());
            LinkedHashMap<String, Object> map = JSONObject.parseObject(item.getDetails(), LinkedHashMap.class);
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                main.put(next.getKey(), next.getValue());
            }
            exportList.add(main);
        });

        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf-8");
            String name = "Omics_Single cell omics_Proteomics_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".csv");
            EasyExcel.write(response.getOutputStream())
                    .head(head(exportList))
                    .excelType(ExcelTypeEnum.CSV)
                    .sheet(fileName)
                    .doWrite(datas(exportList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件编码异常");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(500, "文件导出异常");
        }

    }

    /**
     * 数据处理
     *
     * @param exportList
     * @return
     */
    private List<List<Object>> datas(List<Map<String, Object>> exportList) {
        if (ObjectUtils.isNotEmpty(exportList)) {
            List<List<Object>> datas = ListUtils.newArrayList();
            exportList.forEach(item -> {
                List<Object> data = ListUtils.newArrayList();
                item.forEach((key, value) -> {
                    data.add(value);
                });
                datas.add(data);
            });
            return datas;
        }
        return null;
    }

    /**
     * 表头处理
     *
     * @param exportList
     * @return
     */
    private List<List<String>> head(List<Map<String, Object>> exportList) {
        if (ObjectUtils.isNotEmpty(exportList)) {
            List<List<String>> heads = ListUtils.newArrayList();
            Map<String, Object> map = exportList.get(0);
            map.forEach((key, value) -> {
                List<String> head = ListUtils.newArrayList();
                head.add(key);
                heads.add(head);
            });
            return heads;
        }
        return null;

    }
}

