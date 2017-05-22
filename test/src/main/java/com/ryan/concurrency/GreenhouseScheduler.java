package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/22.
 * Book Page730 Chapter 21.7.5 使用 ScheduledExecutor 的温室控制器
 */

public class GreenhouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day";
    public synchronized String getThermostat(){
        return thermostat;
    }
    public synchronized void setThermostat(String value){
        thermostat = value;
    }
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
    public void schedule(Runnable event, long delay){
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }
    public void repeat(Runnable event, long initialDelay, long period){
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    class LightOn implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here to physically turn on the light
            Util.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here to physically turn off the light
            Util.println("Turning off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here
            Util.println("Turning green house water on");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here
            Util.println("Turning green house water off");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here
            Util.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here
            Util.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        @Override
        public void run() {
            Util.println("Bing!");
        }
    }

    class Terminate implements Runnable {
        @Override
        public void run() {
            Util.println("Terminating");
            scheduler.shutdownNow();
            // Must start a separate task to do this job, since the scheduler has been shut down
            new Thread(){
                @Override
                public void run() {
                    for (DataPoint dataPoint : data) {
                        Util.println(dataPoint);
                    }
                }
            }.start();
        }
    }

    static class DataPoint {
        final Calendar date;
        final float temperature;
        final float humidity;
        public DataPoint(Calendar date, float temperature, float humidity){
            this.date = date;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return date.getTime() + String.format(" temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }

    private Calendar lastTime = Calendar.getInstance();

    {   // Adjust date to the half hour
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 00);
    }

    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random mRandom = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());

    class CollectData implements Runnable {
        @Override
        public void run() {
            Util.println("Collecting data");
            synchronized (this){
                // Pretend the interval is longer than it is
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                // One in 5 chances of reversing the direction
                if (mRandom.nextInt(5) == 4)
                    tempDirection -= tempDirection;
                // Store previous value
                lastTemp = lastTemp + tempDirection * (1.0f + mRandom.nextFloat());
                if (mRandom.nextInt(5) == 4)
                    humidityDirection -= humidityDirection;
                lastHumidity = lastHumidity + humidityDirection * mRandom.nextFloat();
                // Calendar must be cloned, otherwise all DataPoints hold references to the same
                // lastTime. For a basic object like Calendar, clone is OK
                data.add(new DataPoint((Calendar) lastTime.clone(), lastTemp, lastHumidity));
            }
        }
    }

    public static void main(String[] args){
        GreenhouseScheduler house = new GreenhouseScheduler();
        house.schedule(house.new Terminate(), 5000);
        house.repeat(house.new Bell(), 0, 1000);
        house.repeat(house.new ThermostatNight(), 0, 2000);
        house.repeat(house.new LightOn(), 0, 200);
        house.repeat(house.new LightOff(), 0, 400);
        house.repeat(house.new WaterOn(), 0, 600);
        house.repeat(house.new WaterOff(), 0, 800);
        house.repeat(house.new ThermostatDay(), 0, 1400);
        house.repeat(house.new CollectData(), 500, 500);
    }
}
