package com.ryan.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page688 Chapter 21.3.5 临界区
 */

/** 显式 Lock 对象创建临界区 */
class ExplicitPairManager1 extends PairManager{
    private Lock mLock = new ReentrantLock();
    @Override
    public synchronized void increment() {
        mLock.lock();
        try {
            pair.incrementX();
            pair.incrementY();
            store(getPair());
        } finally {
            mLock.unlock();
        }
    }
}

class ExplicitPairManager2 extends PairManager{
    private Lock mLock = new ReentrantLock();
    @Override
    public void increment() {
        Pair temp;
        mLock.lock();
        try {
            pair.incrementX();
            pair.incrementY();
            temp = getPair();
        } finally {
            mLock.unlock();
        }
        store(temp);
    }
}

public class ExplicitCriticalSection {
    public static void main(String[] args){
        PairManager manager1 = new ExplicitPairManager1()
                , manager2 = new ExplicitPairManager2();
        CriticalSection.testApproaches(manager1, manager2);
    }
}
