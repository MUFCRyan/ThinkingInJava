package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page654 Chapter 21.2.1 定义任务
 */

public class MainThread {
    public static void main(String[] args){
        LiftOff liftOff = new LiftOff();
        liftOff.run();
    }
}
