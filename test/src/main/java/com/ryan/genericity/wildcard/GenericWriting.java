package com.ryan.genericity.wildcard;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/11.
 * 超边界类型放松了在可向方法传递的参数的限制
 */

public class GenericWriting {
    static <T> void writeExact(List<T> list, T item){
        list.add(item);
    }

    static List<Fruit> fruits = new ArrayList<>();
    static List<Apple> apples = new ArrayList<>();

    static void f1(){
        writeExact(apples, new Apple());
        writeExact(fruits, new Apple()); // 可以向指定泛型列表中添加子类类型，子类类型至少是该列表泛型定义的类型
        //writeExact(apples, new Fruit()); // 无法向制定泛型列表中添加父类类型，不确定父类类型一定是该列表泛型定义的类型
        Util.println("f1()");
    }

    static <T> void writeWithWildcard(List<? super T> list, T item){
        list.add(item);
    }

    static void f2(){
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruits, new Apple());
        //writeWithWildcard(apples, new Fruit()); // 无法向制定泛型列表中添加父类类型，不确定父类类型一定是该列表泛型定义的类型
        Util.println("f2()");
    }

    public static void main(String[] args){
        f1();
        f2();
    }
}
