package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/5.
 * Book Page500 Chapter 17.10.1 性能测试框架
 */

public class TestParam {
    public final int size;
    public final int loops;
    public TestParam(int size, int loops){
        this.size = size;
        this.loops = loops;
    }

    public static TestParam[] array(int... values){
        int length = values.length / 2;
        TestParam[] params = new TestParam[length];
        int n = 0;
        for (int i = 0; i < length; i++) {
            params[i] = new TestParam(values[n++], values[n++]);
        }
        return params;
    }

    public static TestParam[] array(String[] values){
        int[] valueArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            valueArray[i] = Integer.decode(values[i]);
        }
        return array(valueArray);
    }
}
