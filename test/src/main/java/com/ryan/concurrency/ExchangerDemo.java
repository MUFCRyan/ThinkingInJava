package com.ryan.concurrency;

import com.ryan.generic.generator.BasicGenerator;
import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page735 Chapter 21.7.7
 * Exchanger：任务间交换对象的栅栏；进入栅栏时各自有一个对象，离开时有由之前对象持有的对象；应用场景：一个任务在创建代价高昂的对象，另一个任务消费它们 --> 有更多的对象在被创建的同时被消费
 */

class ExchangerProducer<T> implements Runnable {
    private Generator<T> mGenerator;
    private Exchanger<List<T>> mExchanger;
    private List<T> mHolder;
    ExchangerProducer(Exchanger<List<T>> exchanger, Generator<T> generator, List<T> holder){
        mExchanger = exchanger;
        mGenerator = generator;
        mHolder = holder;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                for (int i = 0; i < ExchangerDemo.SIZE; i++) {
                    mHolder.add(mGenerator.next());
                }
                // Exchange full for empty
                mHolder = mExchanger.exchange(mHolder);
            }
        } catch (InterruptedException e){

        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> mExchanger;
    private List<T> mHolder;
    private volatile T value;
    ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder){
        mExchanger = exchanger;
        mHolder = holder;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                mHolder = mExchanger.exchange(mHolder);
                for (T item : mHolder) {
                    value = item; // Fetch out value
                    mHolder.remove(item); // OK for CopyOnWriteArrayList
                }
            }
        } catch (InterruptedException e){

        }
        Util.println("Final value: " + value);
    }
}

public class ExchangerDemo {
    static int SIZE = 10;
    static int delay = 5; // seconds
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0)
            SIZE = new Integer(args[0]);
        if (args.length > 1)
            delay = new Integer(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> exchanger = new Exchanger<>();
        List<Fat> producerHolder = new CopyOnWriteArrayList<>()
                , consumerHolder = new CopyOnWriteArrayList<>();
        exec.execute(new ExchangerProducer<>(exchanger, BasicGenerator.create(Fat.class), producerHolder));
        exec.execute(new ExchangerConsumer<>(exchanger, consumerHolder));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
}
