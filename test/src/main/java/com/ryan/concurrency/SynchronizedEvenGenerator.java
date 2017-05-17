package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page678 Chapter 21.3.2 解决共享资源竞争
 * 同步时机：正在写的一个变量接下来可能被另一个线程读取或正在读取一个上一次已经被另一个线程写过的变量，就必须使用同步，且读写线程都必须使用相同的监视器锁同步
 */

/** 同步控制 EvenGenerator */
public class SynchronizedEvenGenerator extends IntGenerator {
    private int currentValue = 0;
    @Override
    public synchronized int next() {
        ++currentValue;
        Thread.yield();
        ++currentValue;
        return currentValue;
    }
    public static void main(String[] args){
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}
