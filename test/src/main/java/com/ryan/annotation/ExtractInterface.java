package com.ryan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page629 Chapter 20.3 使用 apt 处理注解
 * 通过使用 AnnotationProcessorFactory，apt 能够为每个它发现的注解生成一个正确的注解处理器；当使用 apt 时必须指明一个工厂类/能找到 apt 所需的工厂类的路径；
 * 使用 apt 生成注解处理器时无法利用反射机制，因为操作的是源码而非编译后的类 —— 不过使用 mirror API 可以解决这个问题 --> 进而使得在源码中就可以查看方法、域、类型；
 *      使用非标准的 -XclassesAsDecls 可在编译后的类中操作注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    public String value();
}
