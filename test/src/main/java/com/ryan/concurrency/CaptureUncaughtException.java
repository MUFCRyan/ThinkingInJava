package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page673 Chapter 21.2.14 捕获异常
 */

class ExceptionThread2 implements Runnable{

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        Util.println("run() by " + thread);
        Util.println("eh = " + thread.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Util.println("caught " + throwable);
    }
}

class HandlerThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable runnable) {
        Util.println(this + " creating new Thread");
        Thread thread = new Thread(runnable);
        Util.println("created " + thread);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        Util.println("eh = " + thread.getUncaughtExceptionHandler());
        return thread;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }
}
