package com.ryan.genericity.generator;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 可计数创建对象
 */

public class CountedObject {
    private static long counter = 0;
    private final long id = counter++;
    public long id(){
        return id;
    }

    @Override
    public String toString() {
        return "CountedObject " + id;
    }
}
