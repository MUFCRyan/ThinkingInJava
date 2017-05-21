package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page715 Chapter 21.5.4 生产者-消费者队列：吐司 BlockingQueue
 * BlockingQueue 产生的简化：使用显式的 wait()、notifyAll() 时存在的类间的耦合消除了，因为每个类都只和其 BlockingQueue 通信；
 *      其同步由队列和系统的设计隐式地处理了
 */

class Toast {
    public enum Status{DRY, BUTTERED, JAMMED}
    private Status mStatus = Status.DRY;
    private final int id;
    public Toast(int id){
        this.id = id;
    }
    public void butter(){
        mStatus = Status.BUTTERED;
    }
    public void jam(){
        mStatus = Status.JAMMED;
    }
    public Status getStatus(){
        return mStatus;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Toast " + id + ": " + mStatus;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

class Toaster implements Runnable {
    private ToastQueue mToastQueue;
    private int count = 0;
    private Random mRandom = new Random(47);
    public Toaster(ToastQueue queue){
        mToastQueue = queue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100 + mRandom.nextInt(500));
                // Make toast
                Toast toast = new Toast(count++);
                Util.println(toast);
                mToastQueue.put(toast);
            }
        } catch (InterruptedException e){
            Util.println("Toaster interrupted");
        }
        Util.println("Toaster off");
    }
}

// Apply butter to toast
class Butterer implements Runnable {
    private ToastQueue dryQueue, butterQueue;
    public Butterer(ToastQueue dryQueue, ToastQueue butterQueue){
        this.dryQueue = dryQueue;
        this.butterQueue = butterQueue;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // Blocks until next piece of toast is available
                Toast toast = dryQueue.take();
                toast.butter();
                Util.print(toast);
                butterQueue.put(toast);
            }
        } catch (InterruptedException e){
            Util.println("Butterer interrupted");
        }
        Util.println("Butter off");
    }
}

// Apply jam to butter
class Jammer implements Runnable {
    private ToastQueue butterQueue, finishedQueue;
    public Jammer(ToastQueue butterQueue, ToastQueue finishedQueue){
        this.butterQueue = butterQueue;
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // Blocks until next piece of toast is available
                Toast toast = butterQueue.take();
                toast.jam();
                Util.print(toast);
                finishedQueue.put(toast);
            }
        } catch (InterruptedException e){
            Util.println("Jammer interrupted");
        }
        Util.println("Jammer off");
    }
}

class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finishedQueue){
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // Blocks until next piece of toast is available
                Toast toast = finishedQueue.take();
                // Verify that the toast is coming in order, and that all pieces are getting jammed
                if (toast.getId() != counter++ || toast.getStatus() != Toast.Status.JAMMED){
                    Util.println(">>>> Error: " + toast);
                    System.exit(1);
                } else {
                    Util.println("Chomp! " + toast);
                }
            }
        } catch (InterruptedException e){
            Util.println("Eater interrupted");
        }
        Util.println("Eater off");
    }
}

public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue()
                , butterQueue = new ToastQueue()
                , finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue, butterQueue));
        exec.execute(new Jammer(butterQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
