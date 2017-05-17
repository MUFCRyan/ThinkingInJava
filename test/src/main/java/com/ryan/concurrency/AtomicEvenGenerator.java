package com.ryan.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page685 Chapter 21.3.4 原子类
 */

public class AtomicEvenGenerator extends IntGenerator{
    private AtomicInteger currentValue = new AtomicInteger(0);
    @Override
    public int next() {
        return currentValue.addAndGet(2);
    }
    public static void main(String[] args){
        EvenChecker.test(new AtomicEvenGenerator());
    }
}
