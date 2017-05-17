package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page679 Chapter 21.3.2 解决共享资源竞争
 * 一般优先使用 synchronized，因其代码量小 --> 用户出错的可能性也会降低；只在解决特殊问题时才使用 Lock 对象
 */

import com.ryan.util.Util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/** 使用 Lock 场景：synchronized 不能尝试获取锁且最终获取锁会失败，或尝试获取锁一段时间，然后放弃它，此时必须使用 Lock */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed(){
        boolean captured = lock.tryLock();
        try {
            Util.println("tryLock(): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }
    public void timed(){
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        try {
            Util.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }
    public static void main(String[] args){
        final AttemptLocking locking = new AttemptLocking();
        locking.untimed();
        locking.timed();

        // Create a separate task to grab the lock
        new Thread(){
            {setDaemon(true);}
            @Override
            public void run() {
                locking.lock.lock();
                Util.println("acquired");
            }
        }.start();

        Thread.yield(); // Give the second task a chance
        locking.untimed();
        locking.timed();
    }
}
