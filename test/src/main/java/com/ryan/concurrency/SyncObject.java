package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page689 Chapter 21.3.6 在其他对象上同步
 */

class DualSync{
    private Object syncObject = new Object();
    public synchronized void f(){
        for (int i = 0; i < 5; i++) {
            Util.println("f()");
            Thread.yield();
        }
    }
    public void g(){
        synchronized (syncObject){
            for (int i = 0; i < 5; i++) {
                Util.println("g()");
                Thread.yield();
            }
        }
    }
}

public class SyncObject {
    public static void main(String[] args){
        DualSync dualSync = new DualSync();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dualSync.f();
            }
        }).start();
        dualSync.g();
    }
}
