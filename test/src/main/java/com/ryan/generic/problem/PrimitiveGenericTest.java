package com.ryan.generic.problem;

import com.ryan.util.Util;

import net.mindview.util.Generator;
import net.mindview.util.RandomGenerator;

/**
 * Created by MUFCRyan on 2017/4/24.
 * 自动包装机制在某些情况下会失效，如下例中的数组填充 —— 自动包装机制不能应用于数组
 */

class FArray {
    public static <T> T[] fill(T[] a, Generator<T> generator){
        for (int i = 0; i < a.length; i++) {
            a[i] = generator.next();
        }
        return a;
    }
}

public class PrimitiveGenericTest {
    public static void main(String[] args){
        String[] strings = FArray.fill(new String[7], new RandomGenerator.String(10));
        for (String string : strings) {
            Util.println(string);
        }

        Integer[] integers = FArray.fill(new Integer[7], new RandomGenerator.Integer(10));
        for (Integer integer : integers) {
            Util.println(integer);
        }

        // This won't compile cause Autoboxing won't save you here
        // int[] ints = FArray.fill(new Integer[7], new RandomGenerator.Integer(10));
    }
}
