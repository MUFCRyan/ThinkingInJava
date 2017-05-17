package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page663 Chapter 21.2.8 后台线程：由后台线程产生的线程都是后台线程
 */

class Daemon implements Runnable {
    private Thread[] mThreads = new Thread[10];
    @Override
    public void run() {
        for (int i = 0; i < mThreads.length; i++) {
            mThreads[i] = new Thread(new DaemonSpawn());
            mThreads[i].start();
            Util.println("DaemonSpawn " + i + " started, ");
        }
        for (int i = 0; i < mThreads.length; i++) {
            Util.println("Thread[" + i + "] isDaemon() = " + mThreads[i].isDaemon() + ", ");
        }
        while (true)
            Thread.yield();
    }
}

class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while (true)
            Thread.yield();
    }
}

public class Daemons {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Daemon());
        thread.setDaemon(true);
        thread.start();
        Util.println("thread.isDaemon() = " + thread.isDaemon() + ", ");
        // Allows the daemon threads to finish their startup processes
        TimeUnit.SECONDS.sleep(1);
    }
}
