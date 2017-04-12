package com.ryan.generic.fibonacci;

import com.ryan.util.Util;

import java.util.Iterator;

/**
 * Created by MUFCRyan on 2017/4/5.
 */

public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
    private int n = 0;

    public IterableFibonacci(int n) {
        this.n = n;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return n > 0;
            }

            @Override
            public Integer next() {
                n --;
                return IterableFibonacci.this.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args){
        for (int n: new IterableFibonacci(20)) {
            Util.print(n + "    ");
        }
    }
}
