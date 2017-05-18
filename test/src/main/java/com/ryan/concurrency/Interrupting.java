package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page695 Chapter 21.4.3 中断：I/O 和 synchronized 块上的等待不可中断 --> 不需要任何 InterruptedException 处理
 * 潜在隐患：因不可中断 I/O 可能锁住多线程
 */

class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Util.println("InterruptedException");
        }
        Util.println("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream in;
    public IOBlocked(InputStream in){
        this.in = in;
    }
    @Override
    public void run() {
        try {
            Util.println("Waiting for read(): ");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()){
                Util.println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        Util.println("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable{
    public synchronized void f(){
        while (true)
            Thread.yield();
    }
    public SynchronizedBlocked(){
        new Thread(){
            @Override
            public void run() {
                f();
            }
        }.start();;
    }
    @Override
    public void run() {
        Util.println("Trying to call f()");
        f();
        Util.println("Exiting SynchronizedBlocked.run()");
    }
}

public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable runnable) throws InterruptedException {
        Future<?> future = exec.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(100);
        Util.println("Interrupting " + runnable.getClass().getSimpleName());
        future.cancel(true);
        Util.println("Interrupt sent to " + runnable.getClass().getSimpleName());
    }
    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        Util.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}
