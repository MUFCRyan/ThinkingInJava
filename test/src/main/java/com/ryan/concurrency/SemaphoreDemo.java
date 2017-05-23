package com.ryan.concurrency;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/23.
 * Book Page733 Chapter 21.7.6 Semaphore：计数信号量允许多个任务同时访问一个资源，可将其看作向外分发使用资源的许可证（实际没有）
 */

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** A task to check a resource out of a pool */
class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;
    public CheckoutTask(Pool<T> pool){
        this.pool = pool;
    }
    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            Util.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            Util.println(this + " checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
    }

    @Override
    public String toString() {
        return "CheckoutTask " + id;
    }
}

public class SemaphoreDemo {
    final static int SIZE = 25;
    public static void main(String[] args) throws InterruptedException {
        Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckoutTask<>(pool));
        }
        Util.println("All CheckoutTask creates");
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            Util.println(i + ": main() thread checked out");
            fat.operation();
            list.add(fat);
        }
        Future<?> blocked = exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // Semaphore prevents additional checkout, so call is blocked
                    pool.checkOut();
                } catch (InterruptedException e) {
                    Util.println("checkOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true); // Break out of blocked call
        Util.println("Checking in objects in " + list);
        for (Fat fat : list) {
            pool.checkIn(fat);
        }
        for (Fat fat : list) {
            pool.checkIn(fat); // Second checkIn ignored
        }
        exec.shutdownNow();
    }
}
