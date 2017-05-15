package com.ryan.annotation;

import java.util.LinkedList;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page641 Chapter 20.5.1 将 @Unit 用于泛型
 */

public class StackL<T> {
    private LinkedList<T> list = new LinkedList<>();
    public void push(T value){
        list.addFirst(value);
    }
    public T top(){
        return list.getFirst();
    }
    public T pop(){
        return list.removeFirst();
    }
}
