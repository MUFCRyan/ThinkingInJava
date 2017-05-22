package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/22.
 * Book Page722 Chapter 21.7 新类库中的构件
 * Book Page722 Chapter 21.7.1 CountDownLatch：同步数个任务，计数值不能重置
 */

class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random mRandom = new Random(47);
    private final CountDownLatch mLatch;
    public TaskPortion(CountDownLatch latch){
        mLatch = latch;
    }
    @Override
    public void run() {
        try {
            doWork();
            mLatch.countDown();
        } catch (InterruptedException e) {
            // Accept way to exit
        }
    }
    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(mRandom.nextInt(2000));
        Util.println(this + " completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

class WaitingTask implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch mLatch;
    WaitingTask(CountDownLatch latch){
        mLatch = latch;
    }
    @Override
    public void run() {
        try {
            mLatch.await();
            Util.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            Util.println(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

public class CountDownLatchDemo {
    static final int SIZE = 100;
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object
        CountDownLatch latch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(latch));
        }
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(latch));
        }
        Util.println("launched all tasks");
        exec.shutdownNow();
    }
}
