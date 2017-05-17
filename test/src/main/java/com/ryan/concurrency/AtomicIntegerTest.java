package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page684 Chapter 21.3.4 原子类
 * 使用后不需要再依赖于锁，特殊情况下使用，通常使用锁（synchronized、Lock）更安全
 */

public class AtomicIntegerTest implements Runnable {
    private AtomicInteger mInteger = new AtomicInteger();
    public int getValue(){
        return mInteger.get();
    }
    private void evenIncrement(){
        mInteger.addAndGet(2);
    }
    @Override
    public void run() {
        while (true){
            evenIncrement();
        }
    }
    public static void main(String[] args){
       new Timer().schedule(new TimerTask() {
           @Override
           public void run() {
               System.err.println("Aborting");
               System.exit(0);
           }
       }, 5000);
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicIntegerTest test = new AtomicIntegerTest();
        exec.execute(test);
        while (true){
            int value = test.getValue();
            if (value % 2 != 0){
                Util.println(value);
                System.exit(0);
            }
        }
    }
}
