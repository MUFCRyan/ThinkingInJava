package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page718 Chapter 21.6 死锁
 */

public class Chopstick {
    private boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while (taken){
            wait();
        }
        taken = true;
    }
    public synchronized void drop(){
        taken = false;
        notifyAll();
    }
}
