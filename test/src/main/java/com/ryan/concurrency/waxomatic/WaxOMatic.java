package com.ryan.concurrency.waxomatic;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page702 Chapter 21.5 线程间协作
 * Book Page703 Chapter 21.5.1 wait()/notifyAll()：必须使用 while 包裹 wait()
 */

class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (!waxOn)
            wait();
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn)
            wait();
    }
}

class WaxOn implements Runnable {
    private Car mCar;

    public WaxOn(Car car) {
        mCar = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Util.println("Wax On! ");
                TimeUnit.MILLISECONDS.sleep(200);
                mCar.waxed();
                mCar.waitForBuffing();
            }
        } catch (InterruptedException e) {
            Util.println("Exiting via InterruptedException");
        }
        Util.println("Ending Wax On task");

    }
}

class WaxOff implements Runnable {
    private Car mCar;

    public WaxOff(Car car) {
        mCar = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                mCar.waitForWaxing();
                Util.println("Wax Off! ");
                TimeUnit.MILLISECONDS.sleep(200);
                mCar.buffed();
            }
        } catch (InterruptedException e) {
            Util.println("Exiting via InterruptedException");
        }
        Util.println("Ending Wax Off task");
    }
}

public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
