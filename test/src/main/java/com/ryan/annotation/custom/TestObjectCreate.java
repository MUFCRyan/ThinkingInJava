package com.ryan.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page642 Chapter 20.5.3 实现 @Unit：
 *      1. 定义所有的注解类型：所有测试的保留属性必须是 Runtime，因为 @Unit 系统必须在编译后的代码中查询这些注解
 *      2. 使用反射抽取注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestObjectCreate {}
