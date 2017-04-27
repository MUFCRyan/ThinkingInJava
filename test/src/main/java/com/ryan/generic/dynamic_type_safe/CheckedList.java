package com.ryan.generic.dynamic_type_safe;

import com.ryan.generic.coffee.Capuccino;
import com.ryan.generic.coffee.Coffee;
import com.ryan.generic.coffee.Latte;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/27.
 * Book Page409 Chapter 15.13 动态类型安全
 * java.util.Collections 中的 checkedCollection/List/Map/Set/SortedMap/SortedSet() 可以检查在向 Java SE5
 * 之前的代码传递泛型容器的情况下的类型检查问题
 */

public class CheckedList {
    static void oldStyleMethod(List probablyLattes){
        probablyLattes.add(new Capuccino());
    }

    public static void main(String[] args){
        List lattes = new ArrayList<Latte>();
        oldStyleMethod(lattes);
        List<Latte> lattes2 = Collections.checkedList(new ArrayList<Latte>(), Latte.class);
        try {
            // java.lang.ClassCastException: Attempt to insert class com.ryan.generic.coffee.Capuccino element into collection with element type class com.ryan.generic.coffee.Latte
            oldStyleMethod(lattes2);
        } catch (Exception e){
            Util.println(e);
        }

        // Derived types work fine
        List<Coffee> coffees = Collections.checkedList(new ArrayList<Coffee>(), Coffee.class);
        coffees.add(new Latte());
        coffees.add(new Capuccino());
    }
}
