package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page737 Chapter 21.8 仿真：并发使得仿真的每个构件可以成为其自身的任务 --> 仿真更易编程
 * Book Page737 Chapter 21.8.1 银行出纳员仿真，代表场景：对象随机出现且要求由数量有限的服务器提供随机数量的访问服务时间；通过构建仿真可以确定理想的服务器数量
 */

import com.ryan.util.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** Read-only objects don't require synchronization */
class Customer{
    private final int serviceTime;
    public Customer(int time){
        serviceTime = time;
    }
    public int getServiceTime(){
        return serviceTime;
    }
    @Override
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

/** Teach the customer line to display itself */
class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    @Override
    public String toString() {
        if (this.size() == 0)
            return "[Empty]";
        StringBuilder builder = new StringBuilder();
        for (Customer customer : this) {
            builder.append(customer);
        }
        return builder.toString();
    }
}

/** Randomly add customers to a queue */
class CustomerGenerator implements Runnable {
    private CustomerLine mLine;
    private static Random sRandom = new Random(47);
    public CustomerGenerator(CustomerLine line){
        mLine = line;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(sRandom.nextInt(300));
                mLine.put(new Customer(sRandom.nextInt(1000)));
            }
        } catch (InterruptedException e){
            Util.println("CustomerGenerator interrupted");
        }
        Util.println("CustomerGenerator terminating");
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    // Customers served during this shift
    private int customersServed = 0;
    private CustomerLine mLine;
    private boolean servingCustomerLine = true;
    public Teller(CustomerLine line){
        mLine = line;
    }
    // Used by priority queue
    @Override
    public synchronized int compareTo(Teller other) {
        return customersServed < other.customersServed ? -1 : (customersServed > other.customersServed ? 1 : 0);
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Customer customer = mLine.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this){
                    customersServed++;
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e){
            Util.println(this + " interrupted");
        }
        Util.println(this + " terminating");
    }
    public synchronized void doSomethingElse(){
        customersServed = 0;
        servingCustomerLine = false;
    }
    public synchronized void serveCustomerLine(){
        assert !servingCustomerLine:"already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }
    @Override
    public String toString() {
        return "Teller " + id + " ";
    }
    public String shortString(){
        return "T" + id;
    }
}

class TellerManager implements Runnable{
    private ExecutorService mExec;
    private CustomerLine mLine;
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<>();
    private int adjustmentPeriod;
    private static Random sRandom = new Random(47);
    public TellerManager(ExecutorService exec, CustomerLine line, int adjustmentPeriod){
        mExec = exec;
        mLine = line;
        this.adjustmentPeriod = adjustmentPeriod;
        // Start with a single teller
        Teller teller = new Teller(mLine);
        mExec.execute(teller);
        workingTellers.add(teller);
    }
    public void adjustTellerNumber(){
        // This is an actually a control system. By adjusting the numbers, you can reveal stability issues in the control mechanism
        // If line is too long, add another teller
        if (mLine.size() / workingTellers.size() > 2){
            // If tellers are on break or doing another job, bring one back
            if (tellersDoingOtherThings.size() > 0){
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }
            // Else create (hire) a new teller
            Teller teller = new Teller(mLine);
            mExec.execute(teller);
            workingTellers.offer(teller);
            return;
        }
        // If line is short enough, remove a teller
        if (workingTellers.size() > 1 && mLine.size() / workingTellers.size() < 2){
            reassignOneTeller();
        }
        // If there is no line, we only need one teller
        if (mLine.size() == 0){
            while (workingTellers.size() > 1)
                reassignOneTeller();
        }
    }
    // Give a teller a different job or a break
    private void reassignOneTeller(){
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.offer(teller);
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();
                Util.print(mLine + "{ ");
                for (Teller teller : workingTellers) {
                    Util.print(teller.shortString() + " ");
                }
                Util.println("}");
            }
        } catch (InterruptedException e){
            Util.println(this + " interrupted");
        }
        Util.println(this + " terminating");
    }

    @Override
    public String toString() {
        return "TellerManager ";
    }
}

public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;
    public static void main(String[] args) throws InterruptedException, IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        // If line is too long, customers will leave
        CustomerLine line = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(line));
        // Manager will add and remove tellers as necessary
        exec.execute(new TellerManager(exec, line, ADJUSTMENT_PERIOD));
        if (args.length > 0)
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            Util.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
