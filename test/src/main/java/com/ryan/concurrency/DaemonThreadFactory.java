package com.ryan.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page663 Chapter 21.2.8 后台线程
 */

public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnable) {
        Thread daemon = new Thread(runnable);
        daemon.setDaemon(true);
        return daemon;
    }
}
