package com.ryan.containers;

import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page460 Chapter 17.2.1 一种 Generator 解决方案
 */

/**
 * 是适配器设计模式的一个实例，将 Generator 适配到 Collection 上
 */
public class CollectionData<T> extends ArrayList<T>{
    public CollectionData(Generator<T> generator, int quantity){
        for (int i = 0; i < quantity; i++) {
            add(generator.next());
        }
    }

    // 泛型便利方法可以减少在使用类时所必须的类型检查数量
    public static <T> CollectionData<T> list(Generator<T> generator, int quantity){
        return new CollectionData<T>(generator, quantity);
    }
}

class Government implements Generator<String>{
    String[] foundation = ("Strange women lying in ponds distributing swords is no basis for a " +
            "system of government").split(" ");
    private int index;
    @Override
    public String next() {
        return foundation[index++];
    }
}

class CollectionDataTest{
    public static void main(String[] args){
        Set<String> set = new LinkedHashSet<>(new CollectionData<>(new Government(), 15));
        Util.println(set);
        Util.println("-----------------------------------------------------------------");
        // Using the convenience method
        set.addAll(CollectionData.list(new Government(), 15));
        Util.println(set);
    }
}