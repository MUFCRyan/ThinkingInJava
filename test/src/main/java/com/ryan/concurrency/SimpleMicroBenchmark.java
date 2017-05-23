package com.ryan.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page748 Chapter 21.9 性能调优
 * Book Page748 Chapter 21.9.1 比较各类互斥技术
 */

abstract class Incrementable{
    protected long counter = 0;
    public abstract void increment();
}

class SynchronizingTest extends Incrementable {
    @Override
    public synchronized void increment() {
        ++counter;
    }
}

class LockingTest extends Incrementable {
    private Lock mLock = new ReentrantLock();
    @Override
    public void increment() {
        mLock.lock();
        try {
            ++counter;
        } finally {
            mLock.unlock();
        }
    }
}

/**
 * 微基准测试：通常指在隔离的、脱离上下文环境的情况下对某个特性进行性能测试
 * 此处演示的微基准测试不能真正的反映出 synchronized 和 Lock 的性能差别，原因：
 *      1. 最重要的是只有在互斥存在竞争条件的情况下才能看到真正的性能差异 --> 必须有多个任务尝试访问互斥代码区
 *      2. 编译器看到 synchronized 关键字时可能会执行特殊的优化
 * 为了创建有效的测试需要使程序更加复杂化（实例见：SynchronizationComparisons.java）：
 *      1. 需要多个任务 —— 不只修改内部值，还包括读取这些值得任务（否则优化器可识别出这些值从来都不会被使用）
 *      2. 计算必须足够复杂和不可预测 --> 使得编译器没有机会执行积极优化 —— 可通过预加载一个大型的随机 int 数组并在计算总和时使用它们来实现
 */
public class SimpleMicroBenchmark { // micro-benchmark 微基准测试
    static long test(Incrementable incrementable){
        long start = System.nanoTime();
        for (long i = 0; i < 10000000L; i++) {
            incrementable.increment();
        }
        return System.nanoTime() - start;
    }
    public static void main(String[] args){
        long synchronizedTime = test(new SynchronizingTest());
        long lockTime = test(new LockingTest());
        System.out.printf("synchronized: %1$10d\n", synchronizedTime);
        System.out.printf("Lock:         %1$10d\n", lockTime);
        System.out.printf("Lock/synchronized = %1$.3f\n", (double)lockTime / (double) synchronizedTime);
    }
}
