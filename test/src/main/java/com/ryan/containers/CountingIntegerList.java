package com.ryan.containers;

import com.ryan.util.Util;

import java.util.AbstractList;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page469 Chapter 17.2.3 使用 Abstract 类
 * 为了从 AbstractList 创建只读的 List，必须实现 get() 和 size()，此处再次使用了享元解决方案；当你寻找值时，get() 将产生它，故该 List 实际不必组装
 */

public class CountingIntegerList extends AbstractList<Integer>{
    private int size;
    public CountingIntegerList(int size){
        this.size = size < 0 ? 0 : size;
    }

    @Override
    public Integer get(int index) {
        return Integer.valueOf(index);
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args){
        Util.println(new CountingIntegerList(30));
    }
}
