package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page707 Chapter 21.5.2 notify()/notifyAll()
 * notify()：只能唤醒一个持有指定对象的恰当任务，所有的任务必须等待相同的条件，条件变化时必须只能有一个任务从中受益，以上限制必须对所有可能存在的子类都起作用，否则就应使用
 *      notifyAll()；是针对 notifyAll() 的优化
 * notifyAll()：因某个特定锁被调用时，只有等待该锁的任务才会被唤醒
 * 循环退出的两个条件判断：1. interrupted() 获取返回值；2. 异常捕获，二者都需要判断
 */

class Blocker{
    synchronized void waitingCall(){
        try {
            while (!Thread.interrupted()){
                wait();
                Util.println(Thread.currentThread() + " ");
            }
        } catch (InterruptedException e){

        }
    }

    synchronized void prod(){
        notify();
    }

    synchronized void prodAll(){
        notifyAll();
    }
}

class Task implements Runnable{
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable{
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}

public class NotifyVsNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private boolean prod = true;
            @Override
            public void run() {
                if (prod){
                    Util.print("\nnotify() ");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    Util.print("\nnotifyAll() ");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400);
        TimeUnit.SECONDS.sleep(3);
        timer.cancel();
        Util.println("Timer canceled");
        TimeUnit.MILLISECONDS.sleep(2000);
        Util.println("Task2.blocker.prodAll() ");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        Util.println("Shutting down");
        exec.shutdownNow();
    }
}
