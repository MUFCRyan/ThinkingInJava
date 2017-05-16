package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page655 Chapter 21.2.2 Thread 类
 */

public class BasicThreads {
    public static void main(String[] args){
        Thread thread = new Thread(new LiftOff());
        thread.start();
        Util.println("Waiting for LiftOff");
    }
}
