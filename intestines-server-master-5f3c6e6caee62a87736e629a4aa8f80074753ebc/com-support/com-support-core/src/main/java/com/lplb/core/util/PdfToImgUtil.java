package com.lplb.core.util;

import cn.hutool.core.io.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-27 11:48
 * @Description（描述）：PdfToImgUtil
 */
public class PdfToImgUtil {

    public static void main(String[] args) throws Exception {
//        List<String> list = new ArrayList<>();
//        list.add("D:\\Desktop\\肠科学基因项目\\IntestineDB 20230721\\05.Single cell omics\\Gene expression data\\COAD_IMM_10X_GSE139555\\Dataset COAD_IMM_10X_GSE139555_图");
//        list.add("D:\\Desktop\\肠科学基因项目\\IntestineDB 20230721\\03.Transcriptomic\\Gene expression data\\Homo sapiens\\Boxplot&Dotplot\\GSE127938\\GSE127938_jejunum\\GSE127938_jejunum_dotplot");
//        list.forEach(item -> {
//            File file = new File(item);
//            List<File> files = Arrays.asList(file.listFiles());
//            pdf2Img(files);
//
//        });

    }
//
//    private static void pdf2Img(List<File> files) {
//        // 创建多线程
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(50,
//                500,
//                10,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(100000),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
//        files.forEach(file -> {
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    // 判断是否是目录，如果是目录，则继续循环
//                    if (file.isDirectory()) {
//                        pdf2Img(Arrays.asList(file.listFiles()));
//                    }
//                    // 如果是文件，则判断是否是PDF文件，是PDF文件则转为图片
//                    if (file.isFile()) {
//                        if ("pdf".equals(StringUtils.getFilenameExtension(file.getPath()))) {
//                            pdf2png(file);
//                        }
//                    }
//                }
//            });
//        });
//        executor.shutdown();
//    }

    /**
     * PDF转图片
     *
     * @param sourseFile
     */
    public static void pdf2png(File sourseFile) {
        // 源文件目录地址
        String sourseFilePath = sourseFile.getPath();
        System.out.println("源文件路径：：：：：：" + sourseFilePath);
        sourseFilePath = sourseFilePath.substring(0, sourseFilePath.lastIndexOf("/"));
        // 目标文件地址
        String targtFilePath = sourseFilePath + "_img";
        System.out.println("目标文件路径：：：：：：" + targtFilePath);
        // 目标文件目录不存在需要添加目录
        if (!FileUtil.exist(targtFilePath)) {
            FileUtil.mkdir(targtFilePath);
        }
        // 目标文件名称
//        String targtFileName = sourseFile.getName().replace("pdf", "png");
        String targtFileName = sourseFile.getName();
        targtFileName = targtFileName.substring(0, targtFileName.lastIndexOf(".")).trim();
        targtFileName = targtFileName + ".png";
        // 去掉文件中得空格
        // 目标文件
        File targetFile = new File(targtFilePath + "/" + targtFileName);
        if (!FileUtil.exist(targetFile)) {
            try {
                PDDocument doc = PDDocument.load(sourseFile);
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                for (int i = 0; i < pageCount; i++) {
                    // Windows native DPI
                    BufferedImage image = renderer.renderImageWithDPI(i, 200);
                    ImageIO.write(image, "png", targetFile);
                    image.flush();
                }
                doc.close();
            } catch (IOException e) {
                System.out.println("文件转换失败，文件路径：：：：：：：：：：：：：：" + sourseFile.getPath());
            }
        } else {
            System.out.println("目标文件已存在，不需要转换：：：：：：：：：：：：");
        }

    }

}
