package com.lplb.core.util;

/**
 *   @author Ray-zy
 *   @since 2021/6/30 10:08
 **/
public class BusinessUtil {

    /**
     * 包装密级展示内容
     * @param secretLevel 密级
     * @param businessContent 业务内容
     * @return
     */
    public static String secretLevelWrap(String secretLevel,String businessContent){
        return "【"+secretLevel+"】"+businessContent;
    }
}
