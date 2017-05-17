package com.ryan.concurrency;

import com.ryan.util.Util;

import static java.lang.Thread.sleep;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page666 Chapter 21.2.9 编码的变体：使用内部类将线程代码隐藏在类中
 */

/** Using a named inner class */
class InnerThread1 {
    private int countDown = 5;
    private Inner mInner;
    private class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Util.println(this);
                    if (--countDown == 0)
                        return;
                    sleep(10);
                }
            } catch (InterruptedException e) {
                Util.println("Interrupted");
            }
        }

        @Override
        public String toString() {
            return getName() + ": " + countDown;
        }
    }

    public InnerThread1(String name){
        mInner = new Inner(name);
    }
}

/** Using an anonymous inner class */
class InnerThread2 {
    private int countDown = 5;
    private Thread mThread;
    public InnerThread2(String name){
        mThread = new Thread(name){
            @Override
            public void run() {
                try {
                    while (true){
                        Util.println(this);
                        if (--countDown == 0)
                            return;
                        sleep(10);
                    }
                } catch (InterruptedException e){
                    Util.println("sleep() interrupted");
                }
            }

            @Override
            public String toString() {
                return getName() + ": " + countDown;
            }
        };
        mThread.start();
    }
}

/** Using a named Runnable implementation */
class InnerRunnable1 {
    private int countDown = 5;
    private Inner mInner;
    private class Inner implements Runnable{
        Thread thread;
        Inner(String name){
            thread = new Thread(this, name);
            thread.start();
        }
        @Override
        public void run() {
            try {
                while (true) {
                    Util.println(this);
                    if (--countDown == 0)
                        return;
                    sleep(10);
                }
            } catch (InterruptedException e) {
                Util.println("sleep() interrupted");
            }
        }

        @Override
        public String toString() {
            return thread.getName() + ": " + countDown;
        }
    }

    public InnerRunnable1(String name){
        mInner = new Inner(name);
    }
}

/** Using an anonymous Runnable implementation */
class InnerRunnable2 {
    private int countDown = 5;
    private Thread mThread;
    public InnerRunnable2(String name){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Util.println(this);
                        if (--countDown == 0)
                            return;
                        sleep(10);
                    }
                } catch (InterruptedException e){
                    Util.println("sleep() interrupted");
                }
            }

            @Override
            public String toString() {
                return Thread.currentThread().getName() + ": " + countDown;
            }
        }, name);
        mThread.start();
    }
}

/** A separate method to run some code as a task，若线程只执行辅助操作，此种方式更合适 */
class ThreadMethod {
    private int countDown = 5;
    private Thread mThread;
    private String name;
    public ThreadMethod(String name){
        this.name = name;
    }
    public void runTask(){
        mThread = new Thread(name){
            @Override
            public void run() {
                try {
                    while (true){
                        Util.println(this);
                        if (--countDown == 0)
                            return;
                        sleep(10);
                    }
                } catch (InterruptedException e){
                    Util.println("sleep() interrupted");
                }
            }

            @Override
            public String toString() {
                return getName() + ": " + countDown;
            }
        };

        mThread.start();
    }
}

public class ThreadVariations {
    public static void main(String[] args){
        new InnerThread1("InnerThread1");
        new InnerThread2("InnerThread2");
        new InnerRunnable1("InnerRunnable1");
        new InnerRunnable2("InnerRunnable2");
        new ThreadMethod("ThreadMethod").runTask();
    }
}
