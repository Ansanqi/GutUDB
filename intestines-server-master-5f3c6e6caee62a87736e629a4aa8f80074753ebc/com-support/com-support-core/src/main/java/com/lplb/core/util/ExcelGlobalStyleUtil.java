package com.lplb.core.util;

import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import org.apache.poi.ss.usermodel.*;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-01 14:37
 * @Description（描述）：ExcelGlobalStyleUtil
 */
public class ExcelGlobalStyleUtil {

    /**
     * 设置统一样式
     *
     * @param writer
     */
    public static void setGlobalStyle(ExcelWriter writer) {
        StyleSet styleSet = writer.getStyleSet();
        // 设置居中
        styleSet.setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        // 设置字体COLOR_NORMAL，11，宋体
        styleSet.setFont(Font.COLOR_NORMAL, (short) 11, "宋体", false);
        // 设置边框
        styleSet.setBorder(BorderStyle.NONE, IndexedColors.BLACK1);
    }
}
