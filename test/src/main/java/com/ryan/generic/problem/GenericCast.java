package com.ryan.generic.problem;

import com.ryan.generic.coffee.Capuccino;
import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/26.
 * Book Page402 Chapter 15.11.3 转型和警告
 */

class FixedSizeStack<T> {
    private int index = 0;
    private Object[] storage;
    public FixedSizeStack(int size){
        storage = new Object[size];
    }
    public void push(T item){
        storage[index++] = item;
    }
    @SuppressWarnings("unchecked")// 告知编译器 (T) storage[--index] 的转型是安全的
    public T pop(){
        return (T) storage[--index];
    }
}

public class GenericCast {
    public static final int SIZE = 10;
    public static void main(String[] args){
        FixedSizeStack stack = new FixedSizeStack(SIZE);
        String[] strings = "A B C D E F G H I J".split(" ");
        for (int i = 0; i < strings.length; i++) {
            stack.push(strings[i]);
        }
        for (int i = 0; i < SIZE; i++) {
            String string = (String) stack.pop();
            Util.println(string);
        }
    }
}

/**
 * 以下的类会引发问题，要求使用 -Xlint: unchecked 重新进行编译，问题仍旧存在，要求强制转型却又被告知不应转型
 */
class NeedCasting {
    @SuppressWarnings("unckecked")
    public void f(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
        List<Capuccino> coffees = (List<Capuccino>) in.readObject();
    }
}

/**
 * NeedCasting 出现的问题在 Java SE5 中引入了新的转型形式 —— 通过泛型转型：List.class.cast()
 * 形式，但不能转型到实际类型即：List<Capuccino>.class.cast() 或 (List<Capuccino>)List.class.cast()
 */
class ClassCasting {
    public void f(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
        List<Capuccino> coffees = List.class.cast(in.readObject());
    }
}