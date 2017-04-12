package com.ryan.generic.erase.supplement;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 无任何方式可以推翻底层的数组类型，只能是Object[]
 */

public class GenericArray2<T> {
    private Object[] array;
    public GenericArray2(int size){
        array = new Object[size];
    }
    public void put(int index, T item){
        array[index] = item;
    }
    public T get(int index){
        return (T) array[index];
    }

    public T[] rep(){
        return (T[]) array;
    }

    public static void main(String[] args){
        GenericArray2<Integer> array2 = new GenericArray2<>(5);
        for (int i = 0; i < 5; i++) {
            array2.put(i, i);
        }
        for (int i = 0; i < 5; i++) {
            Util.print(array2.get(i));
        }
        Util.println();
        try {
            Integer[] rep = array2.rep();
        } catch (Exception e){
            Util.println(e);
        }
    }
}
