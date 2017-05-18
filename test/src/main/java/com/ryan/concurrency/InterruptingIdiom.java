package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page700 Chapter 21.4.4 检查中断：所有的需要清理的对象的创建后面必须紧跟 try-finally 子句以确保清理工作一定会发生
 */

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int id) {
        this.id = id;
        Util.println("NeedsCleanup: " + id);
    }

    public void cleanup() {
        Util.println("Cleaning up: " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point1
                NeedsCleanup needsCleanup1 = new NeedsCleanup(1);
                // Start try-finally immediately after definition of needsCleanup1, to guarantee proper cleanup of needsCleanup1
                try {
                    Util.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup needsCleanup2 = new NeedsCleanup(2);
                    // Guarantee proper cleanup of needsCleanup2
                    try {
                        Util.println("Calculating");
                        // A time-consuming, non-blocking operation
                        for (int i = 0; i < 2500000; i++) {
                            d += (Math.PI + Math.E) / d;
                        }
                        Util.println("Finished time-consuming operation");
                    } finally {
                        needsCleanup2.cleanup();
                    }
                }  finally {
                    needsCleanup1.cleanup();
                }
                Util.println("Exiting via while() test");
            }
        } catch (InterruptedException e){
            Util.println("Exiting via InterruptedException");
        }
    }
}

public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        args = new String[1];
        args[0] = 2000 + "";
        if (args.length != 1) {
            Util.println("usage: java InterruptingIdiom delay-in-mS");
            System.exit(1);
        }
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(Integer.parseInt(args[0]));
        thread.interrupt();
    }
}
