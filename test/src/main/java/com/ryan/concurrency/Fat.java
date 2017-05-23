package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page734 Chapter 21.7.6 Semaphore
 * Fat 对象作为任务用于测试签入签出
 */

public class Fat {
    private volatile double d;
    private static int counter;
    private final int id = counter ++;
    public Fat(){
        // Expensive, interruptable operation
        for (int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }
    public void operation(){
        Util.println(this);
    }

    @Override
    public String toString() {
        return "Fat " + id;
    }
}
