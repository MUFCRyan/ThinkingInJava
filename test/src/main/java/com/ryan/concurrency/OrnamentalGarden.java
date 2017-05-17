package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page691 Chapter 21.4 终结任务
 * Book Page692 Chapter 21.4.1 装饰性花园
 */

class Count{
    private int count = 0;
    private Random mRandom = new Random(47);
    // Remove this synchronized keyword to see counting fail
    public synchronized int increment(){
        int temp = count;
        if (mRandom.nextBoolean()){
            Thread.yield();
        }
        return count = ++temp;
    }
    public synchronized int value(){
        return count;
    }
}

class Entrance implements Runnable{
    private static Count count = new Count(); // 所有 Entrance 操纵同一个 Count 对象，所以要对其加同步
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    // Doesn't need synchronized to read
    private final int id;
    private static volatile boolean canceled = false;
    // Atomic operation on a volatile field
    public static void cancel(){
        canceled = true;
    }
    public Entrance(int id){
        this.id = id;
        // Keep this task in a list. Also prevents garbage collection of dead tasks
        entrances.add(this);
    }
    @Override
    public void run() {
        while (!canceled){
            synchronized (this){
                ++number;
            }
            Util.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e){
                Util.println("sleep interrupted");
            }
        }
        Util.println("Stooping " + this);
    }
    public synchronized int getValue(){
        return number;
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }
    public static int getTotalCount(){
        return count.value();
    }
    public static int sumEntrances(){
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        // Run for a while, then stop and collect the data
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)){
            Util.println("Some tasks were not terminated");
        }
        Util.println("Total: " + Entrance.getTotalCount());
        Util.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}
