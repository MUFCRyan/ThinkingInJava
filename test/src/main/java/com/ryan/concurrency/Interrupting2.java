package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page700 Chapter 21.4.3 中断
 * ReentrantLock 上的任务具备可以被中断的能力，synchronized 所修饰的方法或临界区则不具备
 */

class BlockedMutex {
    private Lock lock = new ReentrantLock();
    public BlockedMutex(){
        // Acquire it right away, to demonstrate interruption of a task blocked on a ReentrantLocks
        lock.lock();
    }
    public void f(){
        try {
            // This will never be available to a second task
            lock.lockInterruptibly();
            Util.println("lock acquired in f()");
        } catch (InterruptedException e){
            Util.println("Interrupted from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable{
    private BlockedMutex mMutex = new BlockedMutex();
    @Override
    public void run() {
        Util.println("Waiting for f() in BlockedMutex");
        mMutex.f();
        Util.println("Broken out of blocked call");
    }
}

public class Interrupting2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked2());
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        Util.println("Issuing thread.interrupt()");
        thread.interrupt(); // 可以打断被互斥锁所阻塞的调用
    }
}
