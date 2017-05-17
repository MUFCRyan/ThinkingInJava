package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page678 Chapter 21.3.2 解决共享资源竞争
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 显式 Lock 对象 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentValue = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentValue;
            Thread.yield();
            ++currentValue;
            return currentValue; // return 必须在 unlock() 之前调用以确保不会因 unlock() 过早调用导致将数据暴露给下一个任务
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args){
        EvenChecker.test(new MutexEvenGenerator());
    }
}
