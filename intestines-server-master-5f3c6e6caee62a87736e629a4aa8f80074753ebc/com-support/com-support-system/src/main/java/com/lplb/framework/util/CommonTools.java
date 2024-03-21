package com.lplb.framework.util;

import com.lplb.core.context.constant.ConstantContextHolder;

/**
 *   @author Ray-zy
 *   @since 2021/12/21 17:13
 **/
public class CommonTools {

    /**
     * 检查文件的地址
     * @param path
     * @return
     */
    public static String checkFilePath(String path){
        if(!path.contains("http")){
            path = ConstantContextHolder.getOSSDomain() + "/" +path;
        }
        return path;
    }
}
