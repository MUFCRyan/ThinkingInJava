package com.ryan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page656 Chapter 21.2.3 使用 Executor：代为管理 Thread 对象，简化并发编程；允许管理异步任务的执行，而无需显式的管理线程的生命周期
 */

public class CachedThreadPool {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown(); // 防止新任务被提交给该 Executor
    }
}
