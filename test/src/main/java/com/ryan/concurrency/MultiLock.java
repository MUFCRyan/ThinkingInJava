package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page699 Chapter 21.4.3 中断：被互斥锁所阻塞
 * 若 if(count-- > 0) 的条件一直为 true 就会导致该线程阻塞
 */

public class MultiLock {
    public synchronized void f1(int count){
        if (count-- > 0){
            Util.println("f1() calling f2() with count " + count);
            f2(count);
        }
    }

    private synchronized void f2(int count){
        if (count-- > 0){
            Util.println("f2() calling f1() with count " + count);
            f1(count);
        }
    }

    public static void main(String[] args){
        MultiLock multiLock = new MultiLock();
        new Thread(){
            @Override
            public void run() {
                multiLock.f1(10);
            }
        }.start();
    }
}
