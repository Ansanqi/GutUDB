package com.lplb.modular.service;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-09 22:40
 * @Description（描述）：PdfToImgService
 */
public interface PdfToImgService {

    /**
     * PDF转图片
     *
     * @param filePath
     * @return
     */
    Boolean pdfToImg(String filePath);

    /**
     * 文件重命名
     *
     * @param filePath
     * @return
     */
    Boolean fileRename(String filePath);
}
