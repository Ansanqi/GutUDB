package com.lplb.framework.util;

import cn.hutool.http.useragent.OS;

import java.net.URL;

/**
 *   @author Ray-zy
 *   @since 2021/9/28 13:36
 **/
public class OSUtil {
    public static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
    public static final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
    public static final String CLASS_PATH;
    public static final boolean isLinux;
    static {
        URL resource = OS.class.getResource("OS.class");
        String classPath = resource.getPath();
        String className = OS.class.getName().replace('.', '/') + ".class";
        String classesPath = classPath.substring(0, classPath.indexOf(className));
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS") && classesPath.startsWith("/")) {
            classesPath = classesPath.substring(1);
            isLinux = false;
        } else {
            isLinux = true;
        }
        CLASS_PATH = classesPath;
    }
}
