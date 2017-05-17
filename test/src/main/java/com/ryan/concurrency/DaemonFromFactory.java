package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page663 Chapter 21.2.8 后台线程
 */

public class DaemonFromFactory implements Runnable{

    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                Util.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e){
            Util.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for (int i = 0; i < 10; i++) {
            exec.execute(new DaemonFromFactory());
        }
        Util.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}
