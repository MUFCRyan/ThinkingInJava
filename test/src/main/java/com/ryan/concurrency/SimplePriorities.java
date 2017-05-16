package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page659 Chapter 21.2.6 优先级：一般只使用 MAX/NORM/MIN_PRIORITY
 */

public class SimplePriorities implements Runnable {
    private int countDown = 5;
    private volatile double d; // Not optimization --> 确保无任何 编译器优化
    private int priority;
    public SimplePriorities(int priority){
        this.priority = priority;
    }

    @Override
    public String toString() {
        return Thread.currentThread() + ": " + countDown;
    }
    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true){
            // An expensive, interruptable operation
            for (int i = 0; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double) i;
                if (i % 1000 == 0){
                    Thread.yield();
                }
            }
            Util.println(this);
            if (countDown-- == 0)
                return;
        }
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
