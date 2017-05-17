package com.ryan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page672 Chapter 21.2.14 捕获异常
 */

public class ExceptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}
