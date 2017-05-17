package com.ryan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page674 Chapter 21.2.14 捕获异常
 * 为 Thread 设置默认的异常捕获处理器，在没有特定处理器的时候会启用
 */

public class SettingDefaultHandler {
    public static void main(String[] args){
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}
