package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page670 Chapter 21.2.12 创建有响应的用户界面
 */

class UnresponsiveUI {
    private volatile double d = 1;
    public UnresponsiveUI() throws IOException {
        while (d > 0){
            d = d + (Math.PI + Math.E) / d;
            System.in.read();
        }
    }
}

public class ResponsiveUI extends Thread {
    private static volatile double d = 1;
    public ResponsiveUI(){
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        while (true){
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws IOException {
        // new UnresponsiveUI(); // Must kill the progress
        new ResponsiveUI();
        System.in.read();
        Util.println(d); // Shows progress
    }
}
