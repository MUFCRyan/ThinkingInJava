package com.ryan.annotation.database;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page625 Chapter 20.2.3 生成外部文件
 */

public @interface Uniqueness {
    Constraints contraints() default @Constraints(unique = true);
}
