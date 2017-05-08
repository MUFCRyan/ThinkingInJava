package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/5.
 * Book Page499 Chapter 17.10 选择接口的不同实现
 * 实际上只有四种容器：List、Set、Map、Queue，但是有很多衍生类供使用；各种不同的容器之间的主要区别在于其所使用的接口所实现的数据结构类型
 * Book Page499 Chapter 17.10.1 性能测试框架
 */

public abstract class Test<C> {
    String name;
    public Test(String name){
        this.name = name;
    }

    abstract int test(C container, TestParam tp);
}
