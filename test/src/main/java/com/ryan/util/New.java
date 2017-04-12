package com.ryan.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 工具类，创建各种容器，类型参数推断避免了重复的泛型参数列表，但只对赋值操作有效
 */

public class New {
    public static <K, V> Map<K, V> map(){
        return new HashMap<K, V>();
    }

    public static <T> List<T> list(){
        return new ArrayList<T>();
    }

    public static <T> LinkedList<T> lList(){
        return new LinkedList<T>();
    }

    public static <T> Set<T> set(){
        return new HashSet<T>();
    }

    public static <T> LinkedList<T> queue(){
        return new LinkedList<T>();
    }

    public static void main(String[] args){
        Map<String, List<String>> map = New.map();
        List<String> list = New.list();
        LinkedList<String> lList = New.lList();
        Set<String> set = New.set();
        LinkedList<String> queue = New.queue();
    }
}
