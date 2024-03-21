package com.lplb.core.util;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 20:01
 * @Description（描述）：StringUtils
 */
public class StringUtils {

    public static void main(String[] args){
        System.out.println(camelToUnderline("abcDefGhi"));

        System.out.println(camelToUnderline("abc123"));

        System.out.println(camelToUnderline("abcDefGhi"));

        System.out.println(camelToUnderline("abcDefGhi"));
    }

    public static String camelToUnderline(String str) {
        if (str == null || "".equals(str.trim())) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
