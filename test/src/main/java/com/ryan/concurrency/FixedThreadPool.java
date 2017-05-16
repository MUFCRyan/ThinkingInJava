package com.ryan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page657 Chapter 21.2.3 使用 Executor
 */

public class FixedThreadPool {
    public static void main(String[] args){
        // Constructor argument is number of threads
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown(); // 防止新任务被提交给该 Executor
    }
}
