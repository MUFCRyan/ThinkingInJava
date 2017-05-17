package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page666 Chapter 21.2.9 编码的变体
 */

public class SelfManaged implements Runnable {
    private int countDown = 5;
    private Thread mThread = new Thread(this);
    public SelfManaged(){
        mThread.start(); // 构造器中不要启动线程，因为其他任务可能在构造器完成之前开始执行 --> 意味着该任务能够访问处于不稳定状态的对象
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true){
            Util.print(this);
            if (--countDown == 0)
                return;
        }
    }

    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            new SelfManaged();
        }
    }
}
