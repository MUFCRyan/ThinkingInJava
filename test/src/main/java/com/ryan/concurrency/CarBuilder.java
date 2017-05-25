package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page744 Chapter 21.8.3 分发工作
 */

class Car {
    private final int id;
    private boolean engine = false
            , driveTrain = false
            , wheels = false;
    public Car(){
        id = -1;
    }
    // Empty Car object
    public Car(int id){
        this.id = id;
    }
    public synchronized int getId() {
        return id;
    }
    public synchronized void addEngine(){
        engine = true;
    }
    public synchronized void addDriveTrain(){
        driveTrain = true;
    }
    public synchronized void addWheels(){
        wheels = true;
    }

    @Override
    public String toString() {
        return "Car " + id + " [" + " engine: " + engine + " driveTrain: " + driveTrain + " wheels: " + wheels + " ]";
    }
}

class CarQueue extends LinkedBlockingQueue<Car>{}

class ChassisBuilder implements Runnable{
    private CarQueue mCarQueue;
    private int counter = 0;
    public ChassisBuilder(CarQueue carQueue){
        mCarQueue = carQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(500);
                // Make chassis
                Car car = new Car(counter++);
                Util.println("ChassisBuilder created " + car);
                mCarQueue.put(car);
            }
        } catch (InterruptedException e){
            Util.println("Interrupted ChassisBuilder");
        }
        Util.println("ChassisBuilder off");
    }
}

class Assembler implements Runnable{
    private CarQueue mChassisQueue, mFinishingQueue;
    private Car mCar;
    private CyclicBarrier mBarrier = new CyclicBarrier(4);
    private RobotPool mRobotPool;
    public Assembler(CarQueue chassisQueue, CarQueue finishingQueue, RobotPool robotPool){
        mChassisQueue = chassisQueue;
        mFinishingQueue = finishingQueue;
        mRobotPool = robotPool;
    }
    public Car car(){
        return mCar;
    }
    public CyclicBarrier barrier(){
        return mBarrier;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // Blocks until chassis is available
                mCar = mChassisQueue.take();
                // Hire robots to perform work
                mRobotPool.hire(EngineRobot.class, this);
                mRobotPool.hire(DriveTrainRobot.class, this);
                mRobotPool.hire(WheelRobot.class, this);
                mBarrier.await(); // Until the robots finish
                // Put car into finishingQueue for further work
                mFinishingQueue.put(mCar);
            }
        } catch (InterruptedException e){
            Util.println("Exiting Assembler via interrupt");
        } catch (BrokenBarrierException e) {
            // This one we want to know about
            throw new RuntimeException(e);
        }
        Util.println("Assembler off");
    }
}

class Reporter implements Runnable {
    private CarQueue mCarQueue;
    public Reporter(CarQueue carQueue){
        mCarQueue = carQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Util.print(mCarQueue.take());
            }
        } catch (InterruptedException e){
            Util.println("Exiting Reporter via interrupt");
        }
        Util.println("Reporter off");
    }
}

abstract class Robot implements Runnable{
    private RobotPool mRobotPool;
    protected Assembler mAssembler;
    public Robot(RobotPool robotPool){
        mRobotPool = robotPool;
    }
    public Robot assignAssembler(Assembler assembler){
        mAssembler = assembler;
        return this;
    }
    private boolean engage = false;
    public synchronized void engage(){
        engage = true;
        notifyAll();
    }
    // The part of run() that's different for each robot
    abstract protected void performService();
    @Override
    public void run() {
        try {
            powerDown(); // Wait until needed
            while (!Thread.interrupted()){
                performService();
                mAssembler.barrier().await(); // Synchronized
                // We're done with that job
                powerDown();
            }
        } catch (InterruptedException e){
            Util.println("Exiting " + this + " via interrupt");
        } catch (BrokenBarrierException e) {
            // This one we want to know about
            throw new RuntimeException(e);
        }
        Util.println(this + " off");
    }
    private synchronized void powerDown() throws InterruptedException {
        engage = false;
        mAssembler = null;
        mRobotPool.release(this);
        while (!engage)
            wait();
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class EngineRobot extends Robot {
    public EngineRobot(RobotPool robotPool) {
        super(robotPool);
    }
    @Override
    protected void performService() {
        Util.println(this + " installing engine");
        mAssembler.car().addEngine();
    }
}

class DriveTrainRobot extends Robot {
    public DriveTrainRobot(RobotPool robotPool) {
        super(robotPool);
    }
    @Override
    protected void performService() {
        Util.println(this + " installing driveTrain");
        mAssembler.car().addDriveTrain();
    }
}

class WheelRobot extends Robot {
    public WheelRobot(RobotPool robotPool) {
        super(robotPool);
    }
    @Override
    protected void performService() {
        Util.println(this + " installing wheels");
        mAssembler.car().addWheels();
    }
}

class RobotPool {
    // Quietly prevents identical entries
    private Set<Robot> pool = new HashSet<>();
    public synchronized void add(Robot robot){
        pool.add(robot);
        notifyAll();
    }
    public synchronized void hire(Class<? extends Robot> robotType, Assembler assembler) throws InterruptedException {
        for (Robot robot : pool) {
            if (robot.getClass().equals(robotType)){
                pool.remove(robot);
                robot.assignAssembler(assembler);
                robot.engage();
                return;
            }
        }
        wait(); // None available
        hire(robotType, assembler); // Try again, recursively
    }
    public synchronized void release(Robot robot){
        add(robot);
    }
}

public class CarBuilder {
    public static void main(String[] args) throws InterruptedException {
        CarQueue chassisQueue = new CarQueue()
                , finishingQueue = new CarQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        RobotPool robotPool = new RobotPool();
        exec.execute(new EngineRobot(robotPool));
        exec.execute(new DriveTrainRobot(robotPool));
        exec.execute(new WheelRobot(robotPool));
        exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
        exec.execute(new Reporter(finishingQueue));
        // Start everything running by producing chassis
        exec.execute(new ChassisBuilder(chassisQueue)); // 之前的流程启动后，Assembler 已经在等待队列内传过来 Car，ChassisBuilder 启动的作用就是不断往队列中添加 Car
        TimeUnit.SECONDS.sleep(7);
        exec.shutdownNow();
    }
}
