package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page683 Chapter 21.3.3 原子性与易变性
 * 读取和复制操作也是非原子性的，必要时要进行同步保护
 */

class CircularSet{
    private int[] array;
    private int length;
    private int index = 0;
    public CircularSet(int size){
        array = new int[size];
        this.length = size;
        // Initialize to a value not produced by the SerialNumberGenerator
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }
    public synchronized void add(int i){
        array[index] = i;
        // Wrap index and write over old elements
        index = ++index % length;
    }
    public synchronized boolean contains(int value){
        for (int i = 0; i < length; i++) {
            if (array[i] == value)
                return true;
        }
        return false;
    }
}

public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable{
        @Override
        public void run() {
            while (true){
                int serialNumber = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serialNumber)){
                    Util.println("Duplicate: " + serialNumber);
                    System.exit(0);
                }
                serials.add(serialNumber);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
        }
        if (args.length > 0){
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
            Util.println("No duplicates detected");
            System.exit(0);
        }
    }
}
