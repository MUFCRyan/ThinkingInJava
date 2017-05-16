package com.ryan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page658 Chapter 21.2.3 使用 Executor
 * SingleThreadExecutor 类似于线程数量为1的 FixedThreadPool，适用于线程中长时间连续运行的事物；不需在资源共享上处理同步
 */

public class SingleThreadExecutor {
    public static void main(String[] args){
        // Constructor argument is number of threads
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown(); // 防止新任务被提交给该 Executor
    }
}
