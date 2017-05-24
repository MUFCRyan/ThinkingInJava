package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/24.
 * Book Page763 Chapter 21.10 活动对象：可串行化消息而非方法 --> 不再需要防备一个任务在其循环过程中被中断的问题；向活动对象发送消息时，消息会转变为任务 -->
 * 任务被插入到对象的队列中等待运行，Future 有助于此种实现
 * 活动对象的意义：
 *      1. 每个对象可拥有自己的工作器线程
 *      2. 每个对象将会维护对其域的全部控制权（普通类只有选择权）
 *      3. 所有活动对象之间的通信将以在这些对象之间的消息的形式发生
 *      4. 活动对象之间的所有消息都要排队
 */

public class ActiveObjectDemo {
    private ExecutorService mExec = Executors.newSingleThreadExecutor();
    private Random mRandom = new Random(47);
    // Insert a random delay to produce the effect of a calculation tim
    private void pause(int factor){
        try {
            TimeUnit.MILLISECONDS.sleep(100 + mRandom.nextInt(factor));
        } catch (InterruptedException e) {
            Util.println("Sleep() interrupted");
        }
    }
    public Future<Integer> calculateInt(int x, int y){
        return mExec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Util.println("starting " + x + " " + y);
                pause(500);
                return x + y;
            }
        });
    }
    public Future<Float> calculateFloat(float x, float y){
        return mExec.submit(new Callable<Float>() {
            @Override
            public Float call() throws Exception {
                Util.println("starting " + x + " " + y);
                pause(2000);
                return x + y;
            }
        });
    }
    public void shutdown(){
        mExec.shutdown();
    }
    public static void main(String[] args){
        ActiveObjectDemo activeObject = new ActiveObjectDemo();
        // Prevents ConcurrentModificationException
        List<Future<?>> results = new CopyOnWriteArrayList<>();
        for (float f = 0.0f; f < 1.0f; f += 0.2f){
            results.add(activeObject.calculateFloat(f, f));
        }
        for (int i = 0; i < 5; i++) {
            results.add(activeObject.calculateInt(i, i));
        }
        Util.println("All async calls made");
        while (results.size() > 0){
            for (Future<?> result : results) {
                if (result.isDone()){
                    try {
                        Util.println(result.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    results.remove(result);
                }
            }
        }
        activeObject.shutdown();
    }
}
