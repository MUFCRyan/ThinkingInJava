package com.ryan.generic.erase.supplement;

import java.lang.reflect.Array;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 类型标记Class<T>被传递到构造器中以便从擦除中恢复 !!IMPORTANT
 */

public class GenericArrayWithTypeToken<T> {
    private T[] array;
    public GenericArrayWithTypeToken(Class<T> type, int size){
        array = (T[]) Array.newInstance(type, size);
    }
    public void put(int index, T item){
        array[index] = item;
    }
    public T get(int index){
        return array[index];
    }
    public T[] rep(){
        return array;
    }
    public static void main(String[] args){
        GenericArrayWithTypeToken<Integer> arrayToken = new GenericArrayWithTypeToken<>(Integer.class, 5);
        Integer[] rep = arrayToken.rep(); // It is work now
    }
}
