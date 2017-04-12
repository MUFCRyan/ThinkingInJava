package com.ryan.generic.erase;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 及时擦除在方法或类内部移除了有关实际类型的信息，编译器仍旧可以确保在方法或类中使用的类型的内部一致性 !!IMPORTANT
 */

public class FilledListMaker<T> {
    List<T> create(T t, int size){ // 边界——进入方法处
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(t); // ——像add()的方式填充的列表可以保存类型信息，直接创建的数组是无法保存的，此处非边界，所以不会有擦除
        }
        return list; // 边界——离开方法处
    }
    public static void main(String[] args){
        FilledListMaker<String> listMaker = new FilledListMaker<>();
        List<String> list = listMaker.create("Ryan", 7);
        Util.println(list);
    }
}
