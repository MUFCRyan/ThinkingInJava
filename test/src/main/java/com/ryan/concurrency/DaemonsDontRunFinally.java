package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/17.
 * Book Page664 Chapter 21.2.8 后台线程：会在不执行 finally 子句的情况下纠正终止其 run()；非后台的 Executor 是更好的方式
 */

class ADaemon implements Runnable {

    @Override
    public void run() {
        try {
            Util.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){
            Util.println("Exiting via InterruptedException");
        } finally {
            Util.println("This should always run?");
        }
    }
}

public class DaemonsDontRunFinally {
    public static void main(String[] args){
        Thread thread = new Thread(new ADaemon());
        //thread.setDaemon(true);
        thread.start();
    }
}
