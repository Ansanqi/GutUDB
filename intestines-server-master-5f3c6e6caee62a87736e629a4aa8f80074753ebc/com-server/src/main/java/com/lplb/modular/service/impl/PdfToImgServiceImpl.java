package com.lplb.modular.service.impl;

import cn.hutool.core.io.FileUtil;
import com.lplb.core.util.PdfToImgUtil;
import com.lplb.modular.service.PdfToImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-09 22:40
 * @Description（描述）：PdfToImgServiceImpk
 */
@Service
@Slf4j
public class PdfToImgServiceImpl implements PdfToImgService {

    @Resource
    private ThreadPoolExecutor executor;

    /**
     * PDF转图片
     *
     * @param filePath
     * @return
     */
    @Override
    public Boolean pdfToImg(String filePath) {
        File file = new File(filePath);
        List<File> files = Arrays.asList(file.listFiles());
        this.pdf2Img(files);
        return true;
    }

    /**
     * 文件重命名
     *
     * @param filePath
     * @return
     */
    @Override
    public Boolean fileRename(String filePath) {
        File file = new File(filePath);
        List<File> files = Arrays.asList(file.listFiles());
        this.rename(files);
        return true;
    }

    public void rename(List<File> files) {
        files.forEach(file -> {
            // 判断是否是目录，如果是目录，则继续循环
            if (file.isDirectory()) {
                rename(Arrays.asList(file.listFiles()));
            } else {
                // 判断是否是图片文件，是则重命名
                if ("png".equals(StringUtils.getFilenameExtension(file.getPath()))) {
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            // 获取文件名
                            String fileName = file.getName();
                            fileName = fileName.substring(0, fileName.lastIndexOf(".")).trim();
                            fileName = fileName + ".png";
                            FileUtil.rename(file, fileName, true);
                        }
                    });
                }
            }
        });
    }

    public void pdf2Img(List<File> files) {
        files.forEach(file -> {
            // 判断是否是目录，如果是目录，则继续循环
            if (file.isDirectory()) {
                pdf2Img(Arrays.asList(file.listFiles()));
            } else if (file.isFile()) {
                // 如果是文件，则判断是否是PDF文件，是PDF文件则转为图片
                if ("pdf".equals(StringUtils.getFilenameExtension(file.getPath()))) {
//                    executor.submit(new Runnable() {
//                        @Override
//                        public void run() {
                    log.info("为PDF文件，开始转换为img.png，文件为：" + file.getPath() + "/" + file.getName());
                    PdfToImgUtil.pdf2png(file);
//                        }
//                    });
                }
            }
        });
    }
}
