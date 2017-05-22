package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/22.
 * Book Page724 Chapter 21.7.2 CyclicBarrier
 */

class Horse implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random mRandom = new Random(47);
    private static CyclicBarrier mBarrier;
    public Horse(CyclicBarrier barrier){
        mBarrier = barrier;
    }
    public synchronized int getStrides(){
        return strides;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    strides += mRandom.nextInt(3); // Produces 0, 1 or 2
                }
                mBarrier.await();
            }
        } catch (InterruptedException e){
            // A legitimate way to exit
        } catch (BrokenBarrierException e) {
            // This one we want to know about
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }
    public String tracks(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            builder.append("*");
        }
        builder.append(id);
        return builder.toString();
    }
}

public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> mHorses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier mBarrier;
    public HorseRace(int nHorses, int pause){
        mBarrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    builder.append("=");
                }
                Util.println(builder);
                for (Horse horse : mHorses) {
                    Util.println(horse.tracks());
                }
                for (Horse horse : mHorses) {
                    if (horse.getStrides() >= FINISH_LINE){
                        Util.println(horse + " won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e){
                    Util.println("Barrier-action sleep interrupted");
                }
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(mBarrier);
            mHorses.add(horse);
            exec.execute(horse);
        }
    }
    public static void main(String[] args){
        int nHorses = 7;
        int pause = 200;
        if (args.length > 0){
            int n = Integer.parseInt(args[0]);
            nHorses = n > 0 ? n : nHorses;
        }
        if (args.length > 1){
            int n = Integer.parseInt(args[1]);
            pause = n > -1 ? n : pause;
        }
        new HorseRace(nHorses, pause);
    }
}
