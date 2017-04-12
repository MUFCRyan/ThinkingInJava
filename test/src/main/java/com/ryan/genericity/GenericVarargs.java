package com.ryan.genericity;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 实现的效果与Arrays.asList()一致
 */

public class GenericVarargs {
    public static <T> List<T> makeList(T... args){
        List<T> list = new ArrayList<>();
        for (T arg : args) {
            list.add(arg);
        }
        return list;
    }

    public static void main(String[] args){
        List<String> list = makeList("A");
        Util.println(list.toString());

        list = Arrays.asList("A", "B", "C");
        Util.println(list.toString());

        list = makeList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        Util.println(list.toString());
    }
}
