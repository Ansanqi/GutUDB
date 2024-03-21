package com.lplb.sys.modular.file.model;

import lombok.Data;

/**
 *   @author Ray-zy
 *   @since 2021/9/28 13:30
 **/
@Data
public class UpLoader {

    private boolean isFolder;
    private boolean isRoot;
    private String id;
    private String fileType;
    private String name;
    private Long size;
    private String relativePath;
    private String paused;
    private String error;
    private String allError;
    private String aborted;
    private String completed;
    private String averageSpeed;
    private String currentSpeed;
    private String _lastProgressCallback;
    private String uniqueIdentifier;
    private String bsl;
    private Long projectId;
    private String libType;
}
