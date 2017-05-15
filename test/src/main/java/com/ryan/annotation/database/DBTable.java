package com.ryan.annotation.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page624 Chapter 20.2.3 生成外部文件
 */

@Target(ElementType.TYPE) // Applies to classes only，省去可应用于所有类型
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    public String name() default "";
}
