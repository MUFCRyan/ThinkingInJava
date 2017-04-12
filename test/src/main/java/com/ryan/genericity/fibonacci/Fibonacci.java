package com.ryan.genericity.fibonacci;

import com.ryan.genericity.generator.Generator;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/5.
 */

public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    @Override
    public Integer next() {
        return fib(count ++);
    }

    private Integer fib(int n) {
        if (n < 2)
            return 1;
        return fib(n -2) + fib(n -1);
    }

    public static void main(String[] args){
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 20; i++) {
            Util.print(fibonacci.next() + " ");
        }
    }
}
