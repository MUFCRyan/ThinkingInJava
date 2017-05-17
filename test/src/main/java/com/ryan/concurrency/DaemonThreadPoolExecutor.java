package com.ryan.concurrency;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page663 Chapter 21.2.8 后台线程
 */

public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
    public DaemonThreadPoolExecutor(){
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DaemonThreadFactory());
    }
}
