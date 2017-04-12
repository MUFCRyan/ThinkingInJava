package com.ryan.genericity.erase.supplement;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 成功创建泛型数组的唯一方式：创建一个被擦除类型的新数组，然后对其转型
 */

public class GenericArray<T> {
    private T[] array;
    @SuppressWarnings("unchecked")
    public GenericArray(int size){
        array = (T[]) new Object[size];
    }

    public void put(int index, T item){
        array[index] = item;
    }

    public T get(int index){
        return array[index];
    }

    // Method that exposes the underlying representation:
    public T[] rep(){
        return array;
    }

    public static void main(String[] args){
        GenericArray<Integer> array = new GenericArray<>(5);
        //Integer[] rep = array.rep(); // This causes a ClassCastException
        Object[] objs = array.rep(); // This is OK
    }
}
