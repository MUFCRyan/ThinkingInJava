package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page709 Chapter 21.5.3 生产者与消费者
 */

class Meal{
    private final int orderNum;
    public Meal(int orderNum){
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }
}

class WaitPerson implements Runnable{
    private Restaurant mRestaurant;
    public WaitPerson(Restaurant restaurant){
        mRestaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (mRestaurant.mMeal == null)
                        wait();
                }
                Util.println("WaitPerson got " + mRestaurant.mMeal);
                synchronized (mRestaurant.mChef){
                    mRestaurant.mMeal = null;
                    mRestaurant.mChef.notifyAll();
                }
            }
        } catch (InterruptedException e){
            Util.println("WaitPerson  interrupted");
        }
    }
}

class Chef implements Runnable{
    private Restaurant mRestaurant;
    private int count = 0;
    public Chef(Restaurant restaurant){
        mRestaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (mRestaurant.mMeal != null)
                        wait();
                }
                if (count++ == 10){
                    Util.println("Out of food, closing");
                    mRestaurant.exec.shutdownNow();
                }
                Util.print("Order up!");
                synchronized (mRestaurant.mWaitPerson){
                    mRestaurant.mMeal = new Meal(count);
                    mRestaurant.mWaitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e){
            Util.println("Chef interrupted");
        }
    }
}

public class Restaurant {
    Meal mMeal;
    WaitPerson mWaitPerson = new WaitPerson(this);
    Chef mChef = new Chef(this);
    ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant(){
        exec.execute(mChef);
        exec.execute(mWaitPerson);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}
