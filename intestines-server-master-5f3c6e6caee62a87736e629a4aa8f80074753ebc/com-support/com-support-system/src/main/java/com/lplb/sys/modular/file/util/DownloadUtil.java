package com.lplb.sys.modular.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.log.Log;
import com.lplb.core.context.requestno.RequestNoContext;
import com.lplb.core.exception.ServiceException;
import com.lplb.sys.modular.file.enums.SysFileInfoExceptionEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 文件下载工具类
 *
 * @author xuyuxiang
 * @date 2020/8/5 21:45
 */
public class DownloadUtil {

    private static final Log log = Log.get();

    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            wrapResponse(response,fileName, (long) fileBytes.length);
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 下载文件异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
            throw new ServiceException(SysFileInfoExceptionEnum.DOWNLOAD_FILE_ERROR);
        }
    }

    public static void download(String fileName,InputStream inputStream ,Long fileSize, HttpServletResponse response) {
        log.info("File stream obtained successfully,Start writing response.......");
        try {
            wrapResponse(response,fileName,fileSize);
            stream(inputStream,response.getOutputStream());
            inputStream.close();
//            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 下载文件异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
            throw new ServiceException(SysFileInfoExceptionEnum.DOWNLOAD_FILE_ERROR);
        }
    }
    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     * @author xuyuxiang
     * @date 2020/8/5 21:46
     */
    public static void download(File file, HttpServletResponse response) {
        // 获取文件字节
        byte[] fileBytes = FileUtil.readBytes(file);
        //获取文件名称
        String fileName;
        try {
            fileName = URLEncoder.encode(file.getName(), CharsetUtil.UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error(">>> 下载文件异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
            throw new ServiceException(SysFileInfoExceptionEnum.DOWNLOAD_FILE_ERROR);
        }
        //下载文件
        download(fileName, fileBytes, response);
    }

    public static long stream(InputStream input, OutputStream output) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output);
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }
            input.close();
            return size;
        }
    }
    public static void wrapResponse(HttpServletResponse response,String fileName,Long fileSize){
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode(fileName) + "\"");
        response.addHeader("Content-Length", "" + fileSize);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream;charset=UTF-8");
    }
}
