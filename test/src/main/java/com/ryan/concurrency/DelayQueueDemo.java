package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by MUFCRyan on 2017/5/22.
 * Book Page726 Chapter 21.7.3 DelayQueue：无界 BlockingQueue，用于放置实现了 Delayed 接口的对象 ——
 *      对象在到期时才能取走，根据到期时间排序
 */

class DelayedTask implements Runnable, Delayed{
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delayInMilliseconds){
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }
    public long getDelay(TimeUnit unit){
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }
    @Override
    public int compareTo(Delayed delayed) {
        DelayedTask that = (DelayedTask) delayed;
        if (trigger < that.trigger)
            return -1;
        if (trigger > that.trigger)
            return 1;
        return 0;
    }
    @Override
    public void run() {
        Util.println(this +" ");
    }
    @Override
    public String toString() {
        return String.format("[%1$-4d] ", delta) + " Task " + id;
    }
    public String summary(){
        return "(" + id + ":" + delta + ")";
    }
    public static class EndSentinel extends DelayedTask {
        private ExecutorService mExec;
        public EndSentinel(int delay, ExecutorService exec) {
            super(delay);
            mExec = exec;
        }

        @Override
        public void run() {
            for (DelayedTask task : sequence) {
                Util.println(task.summary() + " ");
            }
            Util.println(this + " Calling shutdownNow()");
            mExec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> queue;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                queue.take().run(); // Run task with the current task
            }
        } catch (InterruptedException e){
            // Accept way to exit
        }
        Util.println("Finished DelayedTaskConsumer");
    }
}

public class DelayQueueDemo {
    public static void main(String[] args){
        Random random = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        // Fill with tasks that have random delays
        for (int i = 0; i < 20; i++) {
            delayQueue.put(new DelayedTask(random.nextInt(5000)));
        }
        // Set the shopping point
        delayQueue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(delayQueue));
    }
}
