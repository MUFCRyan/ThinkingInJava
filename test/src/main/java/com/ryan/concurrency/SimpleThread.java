package com.ryan.concurrency;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page665 Chapter 21.2.9 编码的变体
 */

public class SimpleThread extends Thread{
    private int countDown = 5;
    private static int threadCount = 0;
    public SimpleThread(){
        // Store the thread name
        super(Integer.toString(++ threadCount));
        start();
    }

    @Override
    public String toString() {
        return "#" + getName() + "(" + countDown + "), ";
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
            new SimpleThread();
        }
    }
}
