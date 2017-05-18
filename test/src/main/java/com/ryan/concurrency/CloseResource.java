package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page697 Chapter 21.4.3 中断：潜在隐患：因不可中断 I/O 可能锁住多线程；解决方案：关闭任务上的底层资源（此例中是输入流：inputStream、System.in）
 */

public class CloseResource {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket socket = new ServerSocket(53874);
        InputStream inputStream = new Socket("localhost", 53874).getInputStream();
        exec.execute(new IOBlocked(inputStream));
        exec.execute(new IOBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(100);
        Util.println("Shutting down all threads");
        exec.shutdownNow();
        TimeUnit.SECONDS.sleep(1);
        Util.println("Closing " + inputStream.getClass().getSimpleName());
        inputStream.close();
        Util.println("Closing " + System.in.getClass().getSimpleName());
        System.in.close();
    }
}
