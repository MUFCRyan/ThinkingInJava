package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page650 Chapter 21 并发
 * Book Page653 Chapter 21.2 基本线程机制
 * Book Page654 Chapter 21.2.1 定义任务
 */

public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount ++;
    public LiftOff(){

    }
    public LiftOff(int countDown){
        this.countDown = countDown;
    }
    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ")";
    }
    @Override
    public void run() {
        while (countDown-- > 0){
            Util.println(status());
            Thread.yield();
        }
    }
}
