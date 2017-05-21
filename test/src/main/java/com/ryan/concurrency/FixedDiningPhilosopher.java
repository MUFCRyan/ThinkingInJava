package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page721 Chapter 21.6 死锁
 */

public class FixedDiningPhilosopher {
    public static void main(String[] args) throws InterruptedException, IOException {
        int ponder = 5;
        if (args.length > 0)
            ponder = Integer.parseInt(args[0]);
        int size = 5;
        if (args.length > 1)
            size = Integer.parseInt(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < size; i++) {
            // changes：通过确保最后一个 Philosopher 先拿起和放下左边的 Chopstick 以移除死锁
            if (i < size - 1)
                exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1)], i, ponder));
            else
                exec.execute(new Philosopher(chopsticks[0], chopsticks[i], i, ponder));

        }
        if (args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            Util.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
