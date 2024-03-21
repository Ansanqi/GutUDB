package com.lplb.core.annotion;

import java.lang.annotation.*;

/**
 *   @author Ray-zy
 *   @since 2020/11/27 15:35
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ThreadLock {
    /**
     * 方法名
     *
     * @return
     */
    String method() default "";
}
