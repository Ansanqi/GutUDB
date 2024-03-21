package com.lplb.sys.modular.file.util;

import cn.hutool.core.io.FileUtil;
import com.lplb.core.exception.ServiceException;
import com.lplb.sys.config.FileConfig;
import com.lplb.sys.modular.file.entity.SysFileChunk;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * 文件工具类
 * @author 洋葱骑士
 *
 */
public class FileInfoUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(FileInfoUtils.class);

    public static String generatePath(String uploadFolder, SysFileChunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
//        	logger.info("path not exist,create path: {}", sb.toString());
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
            	logger.error(e.getMessage(), e);
            }
        }

        return sb.append("/")
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }

    /**
     * 文件合并
     *
     * @param targetFile
     * @param folder
     */
    public static String merge(String file, String folder, String filename){
    	//默认合并成功
    	String rlt = "200";
        Stream<Path> pathStream = null;
        try {
        	//先判断文件是否存在
        	if(fileExists(file)) {
        		//文件已存在
        		rlt = "300";
        	}else {
        		//不存在的话，进行合并
        		Files.createFile(Paths.get(file));
                pathStream = Files.list(Paths.get(folder));
                pathStream.filter(path -> !path.getFileName().toString().equals(filename))
                        .sorted((o1, o2) -> {
                            String p1 = o1.getFileName().toString();
                            String p2 = o2.getFileName().toString();
                            int i1 = p1.lastIndexOf("-");
                            int i2 = p2.lastIndexOf("-");
                            return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                        })
                        .forEach(path -> {
                            try {
                                //以追加的形式写入文件
                                Files.write(Paths.get(file), Files.readAllBytes(path), StandardOpenOption.APPEND);
                                //合并后删除该块
                                Files.delete(path);
                            } catch (IOException e) {
                            	logger.error(e.getMessage(), e);
                            }
                        });
        	}
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        	//合并失败
        	rlt = "400";
        } finally {
            assert pathStream != null;
            pathStream.close();
        }
        
        return rlt;
    }
    
    /**
     * 根据文件的全路径名判断文件是否存在
     * @param file
     * @return
     */
    public static boolean fileExists(String file) {
    	boolean fileExists = false;
    	Path path = Paths.get(file);
    	fileExists = Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
    	return fileExists;
    }


    /**
     * 打包文件
     * @return
     */
    public static boolean zipFile(String filePath, String targetPath) {
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(CompressionMethod.STORE);
        // 压缩级别
        parameters.setCompressionLevel(CompressionLevel.FASTEST);
        parameters.setEncryptionMethod(EncryptionMethod.AES);
        parameters.setEncryptFiles(true);
        // 要打包的文件夹
        File currentFile = new File(filePath);
        try (ZipFile zipFile = new ZipFile(targetPath, FileConfig.ZIP_ENC_KEY.toCharArray());) {
            File[] fs = currentFile.listFiles();
            // 遍历test文件夹下所有的文件、文件夹
            assert fs != null;
            for (File f : fs) {
                if (f.isDirectory()) {
                    zipFile.addFolder(f, parameters);
                } else {
                    zipFile.addFile(f, parameters);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(500, "文件打包失败，请联系管理员");
        }
        return true;
    }

    //获取流文件
    public static void inputStreamToFile(InputStream ins, File file) {
        try (OutputStream os = new FileOutputStream(file);) {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 删除导入的文件
     */
    public static void delImportFile(String filePath) {
        //解析完成之后删除文件包
        FileUtil.del(filePath);
        FileUtil.del(filePath + ".zip");
    }

}
