package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/22.
 * Book Page728 Chapter 21.7.4 PriorityBlockingQueue：具有可阻塞的读取操作
 */

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private static int counter = 0;
    private final int id = counter++;
    private static Random mRandom = new Random(47);
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PrioritizedTask prioritizedTask) {
        return priority < prioritizedTask.priority ? 1 : (priority > prioritizedTask.priority ? -1 : 0);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
        }
        Util.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    // 确保 EndSentinel 是队列中的最后一个对象
    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService mExec;

        public EndSentinel(ExecutorService exec) {
            super(-1); // lowest priority in this program
            mExec = exec;
        }

        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTask task : sequence) {
                Util.print(task.summary());
                if (++count % 5 == 0)
                    Util.println();
            }
            Util.println();
            Util.println(this + " Calling shutdownNow()");
            mExec.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable {
    private Random mRandom = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;

    public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService exec) {
        this.queue = queue;
        this.exec = exec; // Used for EndSentinel
    }

    @Override
    public void run() {
        // Unbounded queue, never blocks
        // Fill it up fast with random priorities
        for (int i = 0; i < 20; i++) {
            queue.add(new PrioritizedTask(mRandom.nextInt(10)));
            Thread.yield();
        }
        // Trickle in highest-priority first
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            // Add jobs, lowest priority first
            for (int i = 0; i < 10; i++) {
                queue.add(new PrioritizedTask(i));
            }
            // A sentinel to stop all the tasks
            queue.add(new PrioritizedTask.EndSentinel(exec));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.println("Finished PrioritizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> queue;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // Use current thread to run the task
                queue.take().run();
            }
        } catch (InterruptedException e){

        }
        Util.println("Finished PrioritizedTaskConsumer");
    }
}

public class PriorityBlockingQueueDemo {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}
