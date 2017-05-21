package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page719 Chapter 21.6 死锁
 */

public class Philosopher implements Runnable {
    private Chopstick left, right;
    private final int id, ponderFactor;
    private Random mRandom = new Random(47);
    private void pause() throws InterruptedException {
        if (ponderFactor == 0){
            return;
        }
        TimeUnit.MILLISECONDS.sleep(mRandom.nextInt(ponderFactor * 250));
    }
    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor){
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Util.println(this + " thinking");
                pause();
                // Philosophers becomes hungry
                Util.println(this + " grabbing right");
                right.take();
                Util.println(this + " grabbing left");
                left.take();
                Util.println(this + " eating");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e){
            Util.println(this + " exiting via interrupt");
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}
