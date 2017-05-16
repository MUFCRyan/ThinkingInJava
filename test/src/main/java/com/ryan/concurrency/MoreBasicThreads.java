package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page655 Chapter 21.2.2 Thread 类
 */

public class MoreBasicThreads {
    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        Util.println("Waiting for LiftOff");
    }
}
