package com.ryan.generic.erase.supplement;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 无法创建new T()是因为擦除以及编译器无法确认T是否具有默认构造器，Java中的解决方式是传递一个工厂对象并用其创建新的实例，最便利的对象就是Class对象
 */

class ClassAsFactory<T> {
    T instance;

    public ClassAsFactory(Class<T> kind) {
        try {
            instance = kind.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

public class InstantiateGenericType {
    public static void main(String[] args) {
        ClassAsFactory<String> stringFactory = new ClassAsFactory<>(String.class);
        Util.println("ClassAsFactory String succeed");
        try {
            ClassAsFactory<Integer> integerFactory = new ClassAsFactory<>(Integer.class);
        } catch (Exception e) {
            Util.println("ClassAsFactory String failed, because of ");
        }
    }
}
