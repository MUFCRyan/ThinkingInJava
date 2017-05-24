package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by MUFCRyan on 2017/5/24.
 * Book Page760 Chapter 21.9.4 ReadWriteLock：对向低写入高读取的数据结构进行了优化；可同时有多个读取者，只要它们都不会写入即可
 */

class ReaderWriterListTest {
    ExecutorService mExec = Executors.newCachedThreadPool();
    private static final int SIZE = 100;
    private static Random sRandom = new Random(47);
    private ReaderWriterList<Integer> mList = new ReaderWriterList<>(SIZE, 0);
    public ReaderWriterListTest(int readers, int writers){
        for (int i = 0; i < readers; i++) {
            mExec.execute(new Reader());
        }
        for (int i = 0; i < writers; i++) {
            mExec.execute(new Writer());
        }
    }
    private class Reader implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()){
                    for (int i = 0; i < SIZE; i++) {
                        mList.get(i);
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                }
            } catch (InterruptedException e){}
        }
    }
    private class Writer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    mList.set(i, sRandom.nextInt());
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e){}
            Util.println("Writer finished, shutting down");
            mExec.shutdownNow();
        }
    }
}

public class ReaderWriterList<T> {
    private ArrayList<T> lockedList;
    // Make the ordering fair
    private ReentrantReadWriteLock mLock = new ReentrantReadWriteLock(true); // true 代表同步，默认 false
    public ReaderWriterList(int size, T initialValue){
        lockedList = new ArrayList<>(Collections.nCopies(size, initialValue));
    }
    public T set(int index, T element){
        Lock writeLock = mLock.writeLock();
        writeLock.lock();
        try {
            return lockedList.set(index, element);
        } finally {
            writeLock.unlock();
        }
    }
    public T get(int index){
        Lock readLock = mLock.readLock();
        readLock.lock();
        try {
            // Show that multiple readers may acquire the read lock
            if (mLock.getReadLockCount() > 1){
                Util.println(mLock.getReadLockCount() + " ");
            }
            return lockedList.get(index);
        } finally {
            readLock.unlock();
        }
    }
    public static void main(String[] args){
        new ReaderWriterListTest(50, 1);
    }
}
