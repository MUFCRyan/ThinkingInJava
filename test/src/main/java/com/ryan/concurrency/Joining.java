package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page670 Chapter 21.2.11 加入一个线程
 * join()：在线程 B 上调用线程 A.join() 的效果是等待直至线程 B 执行完毕，线程 A 才继续执行，在此之前 A 会被挂起；该方法也可以设置超时时间，超时后无论线程 B
 *      是否执行完毕线程 A 都会自动释放开始执行；可用 interrupt() 中断 线程 A 的挂起状态
 */

class Sleeper extends Thread{
    private int duration;
    public Sleeper(String name, int sleepTime){
        super(name);
        duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            Util.println(getName() + " was interrupted. " + "isInterrupted(): " + isInterrupted());
            return;
        }
        Util.println(getName() + " has awakened");
    }
}

class Joiner extends Thread{
    private Sleeper mSleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        mSleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            mSleeper.join();
        } catch (InterruptedException e){
            Util.println("Interrupted");
        }
        Util.println(getName() + " join completed");
    }
}

public class Joining {
    public static void main(String[] args){
        Sleeper sleepy = new Sleeper("Sleepy", 1500)
                , grumpy = new Sleeper("Grumpy", 1500);
        Joiner dopey = new Joiner("Dopey", sleepy)
                , doc = new Joiner("Doc", grumpy);
        grumpy.interrupt();
    }
}
