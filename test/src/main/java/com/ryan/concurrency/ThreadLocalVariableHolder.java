package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page690 Chapter 21.3.7 线程本地存储
 */

class Accessor implements Runnable{
    private final int id;
    public Accessor(int id){
        this.id = id;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThreadLocalVariableHolder.increment();
            Util.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#" + id  + ": " + ThreadLocalVariableHolder.get();
    }
}

/**
 * increment() 和 get() 都不是 synchronized 的，但 ThreadLocal 保证了不会出现竞争条件
 */
public class ThreadLocalVariableHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random mRandom = new Random(47);
        @Override
        protected synchronized Integer initialValue(){
            return mRandom.nextInt(10000);
        }
    };

    public static void increment(){
        value.set(value.get() + 1);
    }

    public static int get() {
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow(); // All Accessors will quit
    }
}
