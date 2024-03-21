package com.lplb.sys.modular.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 *   @author Ray-zy
 *   @since 2021/9/27 17:57
 **/
@Data
@TableName("sys_file_chunk")
public class SysFileChunk implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("id")
    private String id;
    /**
     * 当前文件块，从1开始
     */
    @TableField("chunk_number")
    private Integer chunkNumber;
    /**
     * 每块大小
     */
    @TableField("chunk_size")
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    @TableField("current_chunk_size")
    private Long currentChunkSize;
    /**
     * 总大小
     */
    @TableField("total_size")
    private Long totalSize;
    /**
     * 文件标识
     */
    @TableField("identifier")
    private String identifier;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String filename;
    /**
     * 相对路径
     */
    @TableField("relative_path")
    private String relativePath;
    /**
     * 总块数
     */
    @TableField("total_chunks")
    private Integer totalChunks;
    /**
     * 文件类型
     */
    @TableField("type")
    private String type;

    /**
     * 块内容
     */
    private transient MultipartFile upfile;
}
