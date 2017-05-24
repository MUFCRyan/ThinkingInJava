package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page713 Chapter 21.5.4 生产者-消费者队列
 * LiftOffRunner 中的同步问题已经由 BlockingQueue 解决了
 */

class LiftOffRunner implements Runnable {
    private BlockingQueue<LiftOff> rockets;
    public LiftOffRunner(BlockingQueue<LiftOff> queue){
        rockets = queue;
    }
    public void add(LiftOff liftOff){
        try {
            rockets.put(liftOff);
        } catch (InterruptedException e) {
            Util.println("Interrupted duration put()");
        }
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                LiftOff rocket = rockets.take();
                rocket.run();
            }
        } catch (InterruptedException e){
            Util.println("Walking from take()");
        }
        Util.println("Exiting LiftOffRunner");
    }
}

public class TestBlockingQueues {
    static void getKey(){
        try {
            // Compensate for Windows/Linux difference in the length of the result produced by the Enter key
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void getKey(String message){
        Util.print(message);
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue){
        Util.println(msg);
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread thread = new Thread(runner);
        thread.start();
        for (int i = 0; i < 5; i++) {
            runner.add(new LiftOff(5));
        }
        getKey("Press 'Enter' (" + msg + ")");
        thread.interrupt();
        Util.println("Finished " + msg + " test");
    }

    public static void main(String[] args){
        test("LinkedBlockingQueue", new LinkedBlockingQueue<>()); // Unlimited size
        test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3)); // Fixed size
        test("SynchronousQueue", new SynchronousQueue<>()); // Size of 1
    }
}
