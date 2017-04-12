package com.ryan.generic.erase;

import com.ryan.util.Util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 边界处的动作——数组
 */

public class ArrayMaker<T> {
    private Class<T> kind;
    public ArrayMaker(Class<T> kind){
        this.kind = kind;
    }

    @SuppressWarnings("unchecked")
    T[] create(int size){
        return (T[])Array.newInstance(kind, size);// 在泛型中创建数组推荐使用：Array.newInstance()
    }

    public static void main(String[] args){
        ArrayMaker<String> stringMaker = new ArrayMaker<>(String.class);
        String[] strings = stringMaker.create(10);
        Util.println(Arrays.toString(strings));
    }
}
