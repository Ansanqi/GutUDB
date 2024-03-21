package com.lplb.sys.modular.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.lplb.core.context.constant.ConstantContextHolder;
import com.lplb.core.pojo.response.ErrorResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.framework.util.OSUtil;
import com.lplb.sys.modular.file.entity.SysFileChunk;
import com.lplb.sys.modular.file.model.UpLoader;
import com.lplb.sys.modular.file.model.UploadResult;
import com.lplb.sys.modular.file.service.SysFileChunkService;
import com.lplb.sys.modular.file.service.SysFileInfoService;
import com.lplb.sys.modular.file.util.FileInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *   @author Ray-zy
 *   @since 2021/9/27 18:03
 **/
@RestController
@RequestMapping("/sysFileInfo")
public class SysFileChunkController {

    private final Logger logger = LoggerFactory.getLogger(SysFileChunkController.class);

    private final static String uploadFolder = OSUtil.isWin ? ConstantContextHolder.getDefaultFileUploadPathForWindows() : OSUtil.isMac? "/Users/rayz/fileUpload":ConstantContextHolder.getDefaultFileUploadPathForLinux();

    @Resource
    SysFileChunkService chunkService;

    @Resource
    SysFileInfoService sysFileInfoService;

    /**
     * 上传文件块
     * @param chunk
     * @return
     */
    @PostMapping("/chunk")
    public String uploadChunk(SysFileChunk chunk) {
        chunk.setId(IdUtil.randomUUID());
        String apiRlt = "200";

        MultipartFile file = chunk.getUpfile();
//        logger.info("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunk.getChunkNumber());

        try {
            byte[] bytes = file.getBytes();
            //加密第一个文件块
//            if(chunk.getChunkNumber().equals(1)){
//                bytes = MinioEncryptionUtil.encryption(bytes);
//            }
            Path path = Paths.get(FileInfoUtils.generatePath(uploadFolder, chunk));
            //文件写入指定路径
            Files.write(path, bytes);
//            chunkService.saveOrUpdate(chunk);
            apiRlt = "415";

        } catch (IOException e) {
            apiRlt = "415";
        }
        return apiRlt;
    }

    @GetMapping("/chunk")
    public UploadResult checkChunk(SysFileChunk chunk, HttpServletResponse response) {
        UploadResult ur = new UploadResult();

        //默认返回其他状态码，前端不进去checkChunkUploadedByResponse函数，正常走标准上传
        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

        String file = uploadFolder + "/" + chunk.getIdentifier() + "/" + chunk.getFilename();

        //先判断整个文件是否已经上传过了，如果是，则告诉前端跳过上传，实现秒传
        if (FileInfoUtils.fileExists(file)) {
            ur.setSkipUpload(true);
            ur.setLocation(file);
            response.setStatus(HttpServletResponse.SC_OK);
            ur.setMessage("完整文件已存在，直接跳过上传，实现秒传");
            return ur;
        }

//        //如果完整文件不存在，则去数据库判断当前哪些文件块已经上传过了，把结果告诉前端，跳过这些文件块的上传，实现断点续传
//        ArrayList<Integer> list = chunkService.checkChunk(chunk);
//        if (list != null && list.size() > 0) {
//            ur.setSkipUpload(false);
//            ur.setUploadedChunks(list);
//            response.setStatus(HttpServletResponse.SC_OK);
//            ur.setMessage("部分文件块已存在，继续上传剩余文件块，实现断点续传");
//            return ur;
//        }
        return ur;
    }


    @PostMapping("/mergeFile")
    public Object mergeFile(UpLoader fileInfo) {
        String folder = uploadFolder + "/" + fileInfo.getUniqueIdentifier();
        String rlt = "FALURE";

//        //前端组件参数转换为model对象
//
//        //进行文件的合并操作
        String filename = fileInfo.getName();
        String file = uploadFolder + "/" + fileInfo.getUniqueIdentifier() + "/" + filename;
//        String folder = uploadFolder + "/" + fileInfo.getUniqueIdentifier();
        String fileSuccess = FileInfoUtils.merge(file, folder, filename);
        if (!"200".equals(fileSuccess)&&!"300".equals(fileSuccess)) {
            FileUtil.del(folder);
            return ErrorResponseData.error("文件合并失败，请稍后重试");
        }
        Long fileId = -1L;
        try {
            fileId = sysFileInfoService.uploadFile(FileUtil.file(file));
        }catch (Exception e){
            logger.error(e.getMessage());
            FileUtil.del(folder);
            return ErrorResponseData.error("文件上传失败，请联系管理员");
        }
        //文件合并成功后，保存记录至数据库
//        if("200".equals(fileSuccess)) {
//            rlt = "SUCCESS";
//        }
//
//        //如果已经存在，则判断是否同一个项目，同一个项目的不用新增记录，否则新增
//        if("300".equals(fileSuccess)) {
//            rlt = "SUCCESS";
//        }
//        File delFile = new File(folder);
        FileUtil.del(folder);
        return SuccessResponseData.success(fileId.toString());
//        return SuccessResponseData.success("121212", false);
//        return rlt;
    }
}

