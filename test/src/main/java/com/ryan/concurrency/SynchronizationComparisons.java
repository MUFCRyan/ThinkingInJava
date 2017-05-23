package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page750 Chapter 21.9.1 比较各类互斥技术
 */

abstract class Accumulator {
    public static long cycles = 50000L;
    // Number of Modifiers and Readers during each test
    private static final int N = 4;
    public static ExecutorService sExec = Executors.newFixedThreadPool(N * 2);
    private static CyclicBarrier sBarrier = new CyclicBarrier(N * 2 + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long during = 0;
    protected String id = "error";
    protected final static int SIZE = 100000;
    protected static int[] preLoaded = new int[SIZE];
    static {
        // Load the array of random numbers
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preLoaded[i] = random.nextInt();
        }
    }
    public abstract void accumulate();
    public abstract long read();
    private class Modifier implements Runnable {
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                accumulate();
            }
            try {
                sBarrier.await();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    private class Reader implements Runnable {
        private volatile long value;
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                value = read();
            }
            try {
                sBarrier.await();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    public void timedTest(){
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            sExec.execute(new Modifier());
            sExec.execute(new Reader());
        }
        try {
            sBarrier.await();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        during = System.nanoTime() - start;
        Util.printf("%-13s: %-13d\n", id, during);
    }
    public static void report(Accumulator accumulator1, Accumulator accumulator2){
        Util.printf("%-22s: %.2f\n", accumulator1.id + "/" + accumulator2.id, (double) accumulator1.during / (double) accumulator2.during);
    }
}

class BaseLine extends Accumulator {
    { id = "BaseLine"; }
    @Override
    public void accumulate() {
        try {
            value += preLoaded[index++];
            if (index >= SIZE)
                index = 0;
        } catch (Exception e){
            Util.println("preLoaded.length = " + preLoaded.length + "; index = " + index);
        }
    }
    @Override
    public long read() {
        return value;
    }
}

class SynchronizedTest extends Accumulator {
    { id = "SynchronizedTest"; }
    @Override
    public synchronized void accumulate() {
        value += preLoaded[index++];
        if (index >= SIZE)
            index = 0;
    }
    @Override
    public synchronized long read() {
        return value;
    }
}

class LockTest extends Accumulator {
    { id = "LockTest"; }
    private Lock mLock = new ReentrantLock();
    @Override
    public void accumulate() {
        mLock.lock();
        try {
            value += preLoaded[index++];
            if (index >= SIZE)
                index = 0;
        } finally {
            mLock.unlock();
        }
    }
    @Override
    public long read() {
        mLock.lock();
        try {
            return value;
        } finally {
            mLock.unlock();
        }
    }
}

class AtomicTest extends Accumulator {
    { id = "AtomicTest"; }
    private AtomicInteger index = new AtomicInteger(0);
    private AtomicLong value = new AtomicLong(0);
    @Override
    public void accumulate() {
        // Oops! Relying on more than one Atomic at a time doesn't work. But it still give us a performance indicator
        int i = index.getAndIncrement();
        value.getAndAdd(preLoaded[i]);
        if (++i >= SIZE)
            index.set(0);
    }

    @Override
    public long read() {
        return value.get();
    }
}

public class SynchronizationComparisons {
    static BaseLine sBaseLine = new BaseLine();
    static SynchronizedTest sSynchronizedTest = new SynchronizedTest();
    static LockTest sLockTest = new LockTest();
    static AtomicTest sAtomicTest = new AtomicTest();
    static void test(){
        Util.println("======================================");
        Util.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
        sBaseLine.timedTest();
        sSynchronizedTest.timedTest();
        sLockTest.timedTest();
        sAtomicTest.timedTest();
        Accumulator.report(sSynchronizedTest, sBaseLine);
        Accumulator.report(sLockTest, sBaseLine);
        Accumulator.report(sAtomicTest, sBaseLine);
        Accumulator.report(sSynchronizedTest, sLockTest);
        Accumulator.report(sSynchronizedTest, sAtomicTest);
        Accumulator.report(sLockTest, sAtomicTest);
    }
    public static void main(String[] args){
        int iterations = 5;
        if (args.length > 0)
            iterations = new Integer(args[0]);
        // The first time fills the thread pool
        Util.println("Warm up!");
        sBaseLine.timedTest();
        // Now the initial test doesn't include the cost of starting the threads for the first time
        // Produce multiple data points
        for (int i = 0; i < iterations; i++) {
            test();
            Accumulator.cycles *= 2;
        }
        Accumulator.sExec.shutdownNow();
    }
}
