package com.ryan.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page624 Chapter 20.2.2 默认值限制：元素必须要么具有默认值，要么使用注解时提供元素的值；非基本类型元素的值不能是 null，使用时只能用其他值代替
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulatingNull {
    public int id() default -1;
    public String description() default "";
}
