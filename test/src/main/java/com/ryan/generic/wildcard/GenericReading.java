package com.ryan.generic.wildcard;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/11.
 * 已定义类型的泛型对象只能从该类型容器中读取，其它类型即使是其子类也无法读取
 */

public class GenericReading {

    static <T> T readExact(List<T> list){
        return list.get(0);
    }

    static List<Fruit> fruits = new ArrayList<>();
    static List<Apple> apples = new ArrayList<>();

    static void f1(){
        apples.add(new Apple());
        fruits.add(new Fruit());
        Apple apple = readExact(apples);
        Fruit fruit = readExact(fruits);
        fruit = readExact(apples);
        Util.println("f1()");
    }

    static class Reader<T>{
        T readExact(List<T> list){
            return list.get(0);
        }
    }

    static void f2(){
        Reader<Fruit> reader = new Reader<>();
        Fruit fruit = reader.readExact(fruits);
        // Fruit fruit2 = reader.readExact(apples); //Error: readExact(List<Fruit>) cannot be
        // applied to (List<Apple>)
        Util.println("f2()");
    }

    public static void main(String[] args){
        f1();
        f2();
    }
}
