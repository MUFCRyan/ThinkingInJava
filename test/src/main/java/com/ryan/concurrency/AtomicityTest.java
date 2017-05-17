package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page682 Chapter 21.3.4 原子性与易变性
 */

public class AtomicityTest implements Runnable {
    private int i = 0;
    public int getValue(){
        return i; // 原子性操作，但是缺少同步保护使得该数值可以在处于不稳定的中间状态时被读取（本应一致返回偶数）；此外 i 不是 volatile 还存在可视性问题
    }
    private synchronized void evenIncrement(){
        i++;
        i++;
    }
    @Override
    public void run() {
        while (true)
            evenIncrement();
    }
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest test = new AtomicityTest();
        exec.execute(test);
        while (true){
            int value = test.getValue();
            if (value % 2 != 0){
                Util.println(value);
                System.exit(1);
            }
        }
    }
}
