package com.ryan.concurrency.restaurant2;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page741 Chapter 21.8.2 饭店仿真
 * 添加了更多的仿真组件：Order、Plate --> 充实；了 19 章的Restaurant.java 示例，重用了 19 章中的 menu 类；还引入了 Java SE5 的
 *      SynchronousQueue —— 无内部容量的阻塞队列 --> 每个 put()、take() 都必须相互等待 --> 加强在任何时刻都只能上一道菜的概念
 * 注意：使用队列在任务间进行通信通过反转技术极大地简化了并发编程的过程：任务未直接互相干预，由队列代替互相发送对象；接受任务将处理对象，
 *      将其当做一个消息来对待、而非向其发送消息 -->可极大地提高程序的健壮性
 */

import com.ryan.enumerated.Course;
import com.ryan.enumerated.Food;
import com.ryan.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/** This is given to the waiter, who gives it to the chef */
class Order{ // A data-transfer object
    private static int counter = 0;
    private final int id = counter++;
    private final Customer mCustomer;
    private final WaitPerson mWaitPerson;
    private final Food mFood;
    public Order(Customer customer, WaitPerson waitPerson, Food food){
        mCustomer = customer;
        mWaitPerson = waitPerson;
        mFood = food;
    }
    public Food item(){
        return mFood;
    }
    public Customer getCustomer() {
        return mCustomer;
    }
    public WaitPerson getWaitPerson() {
        return mWaitPerson;
    }

    @Override
    public String toString() {
        return "Order: " + id + " item: " + mFood + " for: " + mCustomer + " served by: " + mWaitPerson;
    }
}

/** That is what comes back from chef */
class Plate {
    private final Order mOrder;
    private final Food mFood;
    public Plate(Order order, Food food){
        mOrder = order;
        mFood = food;
    }
    public Order getOrder() {
        return mOrder;
    }
    public Food getFood() {
        return mFood;
    }

    @Override
    public String toString() {
        return mFood.toString();
    }
}

class Customer implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final WaitPerson mWaitPerson;
    // Only one course at a time can be received
    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();
    public Customer(WaitPerson waitPerson){
        mWaitPerson = waitPerson;
    }
    public void deliver(Plate plate) throws InterruptedException {
        // Only blocks if customer is still eating the previous course
        placeSetting.put(plate);
    }
    @Override
    public void run() {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                mWaitPerson.placeOrder(this, food);
                // Blocks until course has been delivered
                Util.println(this + " eating " + placeSetting.take());
            } catch (InterruptedException e){
                Util.println(this + " waiting for " + course + " interrupted");
                break;
            }
        }
        Util.println(this + " finished meal, leaving");
    }
    @Override
    public String toString() {
        return "Customer " + id + " ";
    }
}

class WaitPerson implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant mRestaurant;
    BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();
    public WaitPerson(Restaurant restaurant){
        mRestaurant = restaurant;
    }
    public void placeOrder(Customer customer, Food food){
        try {
            // Shouldn't actually block because this is a LinkedBlockingQueue with no limit size
            mRestaurant.mOrders.put(new Order(customer, this, food));
        } catch (InterruptedException e) {
            Util.println(this + " placeOrder interrupted");
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // Blocks until a course is ready
                Plate plate = filledOrders.take();
                Util.println(this + "received " + plate + " delivering to " + plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e){
            Util.println(this + " interrupted");
        }
        Util.println(this + " off duty");
    }
    @Override
    public String toString() {
        return "WaitPerson " + id + " ";
    }
}

class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant mRestaurant;
    private static Random sRandom = new Random(47);
    public Chef(Restaurant restaurant){
        mRestaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // Blocks until an order appears
                Order order = mRestaurant.mOrders.take();
                Food food = order.item();
                // Time to prepare food
                TimeUnit.MILLISECONDS.sleep(sRandom.nextInt(300));
                Plate plate = new Plate(order, food);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e){
            Util.println(this + " interrupted");
        }
        Util.println(this + " off duty");
    }
    @Override
    public String toString() {
        return "Chef " + id + " ";
    }
}

class Restaurant implements Runnable {
    private List<WaitPerson> mWaitPersons = new ArrayList<>();
    private List<Chef> mChefs = new ArrayList<>();
    private ExecutorService mExec;
    private static Random sRandom = new Random(47);
    BlockingQueue<Order> mOrders = new LinkedBlockingQueue<>();
    public Restaurant(ExecutorService exec, int nWaitPersons, int nChefs){
        mExec = exec;
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            mWaitPersons.add(waitPerson);
            mExec.execute(waitPerson);
        }
        for (int i = 0; i < nChefs; i++) {
            Chef chef = new Chef(this);
            mChefs.add(chef);
            mExec.execute(chef);
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // A new customer arrives, assign a WaitPerson
                WaitPerson waitPerson = mWaitPersons.get(sRandom.nextInt(mWaitPersons.size()));
                Customer customer = new Customer(waitPerson);
                mExec.execute(customer);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e){
            Util.println("Restaurant interrupted");
        }
        Util.println("Restaurant closing");
    }
}

public class RestaurantWithQueues {
    public static void main(String[] args) throws InterruptedException, IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 5, 2);
        exec.execute(restaurant);
        if (args.length > 0){
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            Util.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
