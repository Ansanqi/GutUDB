package com.lplb.framework.util;

import cn.hutool.crypto.digest.DigestUtil;

import java.io.InputStream;

/**
 *   @author Ray-zy
 *   @since 2021/8/24 10:44
 **/
public class FileMD5Util {

    public static String getFileMD5(InputStream inputStream){
        return DigestUtil.md5Hex(inputStream);
    }


    public static String getFileMD5(byte[] bytes){
        return DigestUtil.md5Hex(bytes);
    }
}
