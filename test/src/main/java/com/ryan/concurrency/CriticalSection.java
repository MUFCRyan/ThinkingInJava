package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page685 Chapter 21.3.5 临界区：只希望多线程同时访问方法内部部分代码的区域，可用同步块控制
 * synchronized 不属于方法特征签名的组成部分，所以在覆盖时可以加上
 * 使用同步控制块比 synchronized 同步整个方法更好的原因：可以使得其它线程能更多的额访问（线程安全的情况下更多）
 */

class Pair{
    private int x, y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Pair(){
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX(){
        x++;
    }

    public void incrementY(){
        y++;
    }

    @Override
    public String toString() {
        return "x: " + x + "    y: " + y;
    }

    public class PairValuesNotEqualException extends RuntimeException{
        public PairValuesNotEqualException(){
            super("Pair values not equal: " + Pair.this);
        }
    }

    // Arbitrary invariant -- both variables must be equal
    public void checkState(){
        if (x != y)
            throw new PairValuesNotEqualException();
    }
}

/** Protect a Pair inside a thread-safe class */
abstract class PairManager{
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair pair = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
    public synchronized Pair getPair(){
        // Make a copy to keep the original safe
        return new Pair(pair.getX(), pair.getY());
    }
    protected void store(Pair pair){
        storage.add(pair);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public abstract void increment();
}

/** Synchronized the entire method */
class PairManager1 extends PairManager{
    @Override
    public synchronized void increment() {
        pair.incrementX();
        pair.incrementY();
        store(getPair());
    }
}

/** Using a critical section */
class PairManager2 extends PairManager{
    @Override
    public void increment() {
        Pair temp;
        synchronized (this){ // 使用 this 同步，若获得了 synchronized 上的锁 --> 该对象其它的 synchronized 方法和临界区就不能被调用了 --> 临界区的效果就会直接缩小在同步的范围内
            pair.incrementX();
            pair.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}

class PairManipulator implements Runnable{
    private PairManager manager;
    public PairManipulator(PairManager manager){
        this.manager = manager;
    }
    @Override
    public void run() {
        while (true){
            manager.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair: " + manager.getPair() + " checkCounter = " + manager.checkCounter.get();
    }
}

class PairChecker implements Runnable{
    private PairManager manager;
    public PairChecker(PairManager manager){
        this.manager = manager;
    }
    @Override
    public void run() {
        while (true){
            manager.checkCounter.incrementAndGet();
            manager.getPair().checkState();
        }
    }
}

public class CriticalSection {
    // Test the two different approaches
    static void testApproaches(PairManager manager1, PairManager manager2){
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator manipulator1 = new PairManipulator(manager1)
                , manipulator2 = new PairManipulator(manager2);
        PairChecker checker1 = new PairChecker(manager1)
                , checker2 = new PairChecker(manager2);
        exec.execute(manipulator1);
        exec.execute(manipulator2);
        exec.execute(checker1);
        exec.execute(checker2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Util.println("Sleep interrupted");
        }
        Util.println("manipulator1: " + manipulator1 + "\nmanipulator2: " + manipulator2);
        System.exit(0);
    }
    public static void main(String[] args){
        PairManager manager1 = new PairManager1()
                , manager2 = new PairManager2();
        testApproaches(manager1, manager2);
    }
}
