package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page662 Chapter 21.2.8 后台线程
 * 只要有任何非后台线程存在程序就不会终止，否则会终止
 * 必须在线程启动之前将其设置为后台线程才有效，由后台线程产生的线程都是后台线程
 */

public class SimpleDaemon implements Runnable{
    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                Util.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e){
            Util.println("sleep() interrupted");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemon());
            daemon.setDaemon(true);
            daemon.start();
        }
        Util.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(175);
    }
}
