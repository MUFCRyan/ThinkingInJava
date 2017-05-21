package com.ryan.concurrency.waxomatic2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page711 Chapter 21.5.3 生产者与消费者：使用显式的 Lock 和 Condition 对象
 * Condition：使用互斥把并允许任务挂起的基本类，方法：await()/signal()/signalAll()
 */

class Car{
    private Lock mLock = new ReentrantLock();
    private Condition mCondition = mLock.newCondition();
    private boolean waxOn = false;
    public void waxed(){
        mLock.lock();
        try {
            waxOn = true;
            mCondition.signalAll();
        } finally {
            mLock.unlock();
        }
    }
    public void buffed(){
        mLock.lock();
        try {
            waxOn = false;
            mCondition.signalAll();
        } finally {
            mLock.unlock();
        }
    }
    public void waitingForWaxing(){

    }
}

public class WaxOMatic2 {
}
