package com.lplb.modular.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lplb.core.exception.ServiceException;
import com.lplb.modular.model.entity.MousecolonRnaGene;
import com.lplb.modular.model.query.MousecolonRnaGeneDetailsQuery;
import com.lplb.modular.model.query.MousecolonRnaQuery;
import com.lplb.modular.service.MousecolonRnaGeneDetailsService;
import com.lplb.modular.service.MousecolonRnaGeneService;
import com.lplb.modular.service.MousecolonRnaService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-07 11:03
 * @Description（描述）：MousecolonRnaServiceImpl
 */
@Service
@Transactional
public class MousecolonRnaServiceImpl implements MousecolonRnaService {

    @Resource
    private MousecolonRnaGeneService mousecolonRnaGeneService;
    @Resource
    private MousecolonRnaGeneDetailsService mousecolonRnaGeneDetailsService;

    @Resource
    private ThreadPoolExecutor executor;
    private final String fileName = "Mutated_Genes";


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
            System.out.println("数据读取完成，开始写入数据：：：：：：：：：：：");

            // 循环
            list.forEach(item -> {
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                // 基因名称
//                String geneName = ObjectUtils.isEmpty(item.get("Gene Name")) ? "NA" : String.valueOf(item.get("Gene Name"));
//                System.out.println("循环写入基因数据，基因名称：：：：：：：：：：" + geneName);
//                // 保存基因名称到主表中
//                MousecolonRnaGene gene = new MousecolonRnaGene();
//                gene.setGeneName(geneName);
//                gene.setOrderBy(list.indexOf(item) + 1);
//                mousecolonRnaGeneService.save(gene);
//
//                // 循环所有字段，依次保存到存值的表中，但是要排除geneName字段
//                item.forEach((key, value) -> {
//                    executor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!"Gene Name".equals(key)) {
//                                MousecolonRnaGeneDetails details = new MousecolonRnaGeneDetails();
//                                details.setGeneId(gene.getId());
//                                details.setDetailsKey(key);
//                                if (ObjectUtils.isNotEmpty(value)) {
//                                    details.setDetailsValue(((Long) value).intValue());
//                                } else {
//                                    details.setDetailsValue(0);
//                                }
//                                mousecolonRnaGeneDetailsService.save(details);
//                            }
//                        }
//                    });
//                });
//                    }
//                });


                // 基因名称
                String geneName = ObjectUtils.isEmpty(item.get("Gene Name")) ? "NA" : String.valueOf(item.get("Gene Name"));
                System.out.println("循环写入基因数据，基因名称：：：：：：：：：：" + geneName);
                // 保存基因名称到主表中
                MousecolonRnaGene gene = new MousecolonRnaGene();
                gene.setGeneName(geneName);
                gene.setOrderBy(list.indexOf(item) + 1);

                // ---------------------------------------------------------
                // 将map中的值，放入转换成json字符串放入数据中，但是要排除geneName字段
                JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
                item.forEach((key, value) -> {
                    if (!"Gene Name".equals(key)) {
                        jsonObject.put(key, value);
                    }
                });
                String details = JSONObject.toJSONString(jsonObject);
                gene.setDetails(details);
                mousecolonRnaGeneService.save(gene);
            });
            System.out.println("数据写入完成，任务结束：：：：：：：：：：：");

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
    public IPage<Map<String, Object>> pageList(MousecolonRnaQuery query) {
        // 分页查询基因列表
        Page<MousecolonRnaGene> page;
        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
            page = new Page<>();
        } else {
            page = new Page<>(query.getCurrent(), query.getSize());
        }

        LambdaQueryWrapper<MousecolonRnaGene> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotEmpty(query.getKeywords())) {
            wrapper.like(MousecolonRnaGene::getGeneName, query.getKeywords());
        }

        wrapper.orderByAsc(MousecolonRnaGene::getOrderBy);
        page = mousecolonRnaGeneService.page(page, wrapper);

        // 返回数据集合
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 循环查询对应基因的前13条详情数据
        page.getRecords().forEach(item -> {
//            LambdaQueryWrapper<MousecolonRnaGeneDetails> detailsWrapper = new LambdaQueryWrapper<>();
//            detailsWrapper.eq(MousecolonRnaGeneDetails::getGeneId, item.getId());
//            detailsWrapper.orderByAsc(MousecolonRnaGeneDetails::getOrderBy);
//            Page<MousecolonRnaGeneDetails> detailsPage = new Page<>(1, 13);
//            detailsPage = mousecolonRnaGeneDetailsService.page(detailsPage, detailsWrapper);
//
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("id", item.getId());
//            map.put("geneName", item.getGeneName());
//
//            detailsPage.getRecords().forEach(i -> {
//                map.put("x-" + i.getDetailsKey(), i.getDetailsValue());
//            });
//            resultList.add(map);


            // 数据列表
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("geneName", item.getGeneName());
            // 取出基因详情，并转化为LinkdHashMap
            LinkedHashMap<String, Object> details = JSONObject.parseObject(item.getDetails(), LinkedHashMap.class);
            // 取出详情的前13个，放入返回结果中
            int i = 0;
            Iterator<Map.Entry<String, Object>> iterator = details.entrySet().iterator();
            while (iterator.hasNext() && i < 13) {
                Map.Entry<String, Object> next = iterator.next();
                map.put("x-" + next.getKey(), next.getValue());
                i++;
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
     * @param query
     * @return
     */
    @Override
    public Map<String, Object> details(MousecolonRnaGeneDetailsQuery query) {
//        LambdaQueryWrapper<MousecolonRnaGeneDetails> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(MousecolonRnaGeneDetails::getGeneId, query.getId());
//        wrapper.orderByAsc(MousecolonRnaGeneDetails::getOrderBy);
//
//        List<MousecolonRnaGeneDetails> list = mousecolonRnaGeneDetailsService.list(wrapper);
//
//        Map<String, Object> result = new LinkedHashMap<>();
//        // 循环添加前缀
//        list.forEach(item -> {
//            result.put("x-" + item.getDetailsKey(), item.getDetailsValue());
//        });
//
        // 查询
        MousecolonRnaGene gene = mousecolonRnaGeneService.getById(query.getId());
        if (ObjectUtils.isEmpty(gene)) {
            return null;
        }
        LinkedHashMap<String, Object> map = JSONObject.parseObject(gene.getDetails(), LinkedHashMap.class);
        // 循环改键
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            result.put("x-" + next.getKey(), next.getValue());
        }
        return result;
    }

    /**
     * Excel导出
     *
     * @param response
     */
    @Override
    public String exportExcel(HttpServletResponse response) {
        // 文件地址
        String filePath = "/file/excels/GSM6578059_mousecolon_RNA.zip";
        return filePath;
//        File file = new File(filePath);
//        if (!FileUtil.exist(file)) {
//            throw new ServiceException(500, "文件不存在");
//        }
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            bis = new BufferedInputStream(new FileInputStream(filePath));
//            bos = new BufferedOutputStream(response.getOutputStream());
//
//            //清空response
//            response.reset();
//
//            //设置response的header
//            //下载的文件名
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filePath.getBytes("UTF-8"), "ISO8859-1"));
//            //文件长度
//            response.addHeader("Content-Length", "" + file.length());
//            //内容类型 application/octet-stream
//            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
//            //字符集
//            response.setCharacterEncoding("UTF-8");
//
//            //写出
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = bis.read(buffer)) != -1) {
//                bos.write(buffer, 0, length);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bis != null) {
//                    bis.close();
//                }
//                if (bos != null) {
//                    bos.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        // 设置响应类型
//        response.setContentType("application/vnd.ms-excel");
//        // 设置字符编码
//        response.setCharacterEncoding("utf-8");
//        // 设置响应头信息
//        try {
//            response.setHeader("Content-disposition",
//                    "attachment;filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8") + ".tsv");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 查询数据集合
//
//        LambdaQueryWrapper<MousecolonRnaGene> wrapper = new LambdaQueryWrapper<>();
//        if (ObjectUtils.isNotEmpty(query.getSearchText())) {
//            wrapper.like(ObjectUtils.isNotEmpty(query.getSearchText()), MousecolonRnaGene::getGeneName, query.getSearchText());
//        }
//        // 分页
//        Page<MousecolonRnaGene> page;
//        if (ObjectUtils.isEmpty(query.getCurrent()) || 0 >= query.getCurrent()) {
//            page = new Page<>();
//        } else {
//            page = new Page<>(query.getCurrent(), query.getSize());
//        }
//        page = mousecolonRnaGeneService.page(page, wrapper);
//        List<MousecolonRnaGene> mainList = page.getRecords();
//
//        // 返回数据集合
//        List<Map<String, Object>> resultList = new ArrayList<>();
//
//        // 循环查询对应基因的详情数据
//        mainList.forEach(item -> {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("geneName", item.getGeneName());
//            // 取出基因详情，并转化为LinkdHashMap
//            LinkedHashMap<String, Object> details = JSONObject.parseObject(item.getDetails(), LinkedHashMap.class);
//            // 取出详情
//            Iterator<Map.Entry<String, Object>> iterator = details.entrySet().iterator();
//            Map.Entry<String, Object> next = iterator.next();
//            while (iterator.hasNext()) {
//                map.put(next.getKey(), next.getValue());
//            }
//            resultList.add(map);
//        });
//
//        // 写入文件
//        ExcelWriter writer = ExcelUtil.getBigWriter();
//        ExcelGlobalStyleUtil.setGlobalStyle(writer);
//
//        writer.write(resultList, true);
//        try {
//            writer.flush(response.getOutputStream(), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            writer.close();
//        }

    }
}
