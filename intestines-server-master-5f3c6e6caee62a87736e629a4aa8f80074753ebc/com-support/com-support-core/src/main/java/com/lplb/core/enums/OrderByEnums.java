package com.lplb.core.enums;

import lombok.Getter;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-08-17 18:05
 * @Description（描述）：OrderByEnums
 */
@Getter
public enum OrderByEnums {

    ASC("ascending", "升序"),
    DESC("descending", "降序");

    private final String name;

    private final String desc;

    OrderByEnums(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
