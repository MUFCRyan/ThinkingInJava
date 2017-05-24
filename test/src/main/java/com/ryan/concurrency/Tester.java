package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/24.
 * Book Page754 Chapter 21.9.2 免锁容器，通用策略：只要读取者只能看到完成的修改结果的情况下，对容器的修改可与读取操作同时发生；
 *      修改是在容器数据结构的某个部分的一个单独的副本上执行且该副本修改过程中不可视；只有当修改完成之后被被修改的结构才会自动与主数据结构进行交换 --> 之后读取者就能看到了
 */

import com.ryan.net.mindview.util.Generated;
import com.ryan.net.mindview.util.RandomGenerator;
import com.ryan.util.Util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 乐观锁 */
public abstract class Tester<C> {
    static int testReps = 10;
    static int testCycles = 1000;
    static int containerSize = 1000;
    abstract C containerInitializer();
    abstract void startReadersAndWriters();
    C testContainer;
    String testId;
    int nReaders, nWriters;
    volatile long readResult = 0, readTime = 0, writeTime = 0;
    CountDownLatch endLatch;
    static ExecutorService sExec = Executors.newCachedThreadPool();
    Integer[] writeData;
    Tester(String testId, int nReaders, int nWriters){
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        this.testId = testId + " " + nReaders + "r " + nWriters + "w";
        writeData = Generated.array(Integer.class, new RandomGenerator.Integer(), containerSize);
        for (int i = 0; i < testReps; i++) {
            runTest();
            readTime = 0;
            writeTime = 0;
        }
    }
    void runTest(){
        endLatch = new CountDownLatch(nReaders + nWriters);
        testContainer = containerInitializer();
        startReadersAndWriters();
        try {
            endLatch.await();
        } catch (InterruptedException e){
            Util.println("endLatch interrupted");
        }
        Util.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
        if (readTime != 0 && writeTime != 0){
            Util.printf("%-27s %14d\n", "readTime + writeTime = ", readTime + writeTime);
        }
    }
    abstract class TestTask implements Runnable {
        abstract void test();
        abstract void putResults();
        long duration;
        @Override
        public void run() {
            long start = System.nanoTime();
            test();
            duration = System.nanoTime() - start;
            synchronized (Tester.this){
                putResults();
            }
            endLatch.countDown();
        }
    }
    public static void initMain(String[] args){
        if (args.length > 0)
            testReps = new Integer(args[0]);
        if (args.length > 1)
            testCycles = new Integer(args[1]);
        if (args.length > 2)
            containerSize = new Integer(args[2]);
        Util.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
    }
}
