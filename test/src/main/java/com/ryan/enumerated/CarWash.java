package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.EnumSet;

/**
 * Created by MUFCRyan on 2017/5/12.
 * Book Page604 Chapter 19.10 常量相关方法
 */

public class CarWash {
    public enum Cycle{
        UNDERBODY{
            @Override
            void action() {
                Util.println("Spraying the underbody");
            }
        },
        WHEELWASH{
            @Override
            void action() {
                Util.println("Washing the wheels");
            }
        },
        PREWASH{
            @Override
            void action() {
                Util.println("Loosening the dirty");
            }
        },
        BASIC{
            @Override
            void action() {
                Util.println("The basic wash");
            }
        },
        HOTWAX{
            @Override
            void action() {
                Util.println("Applying hot wax");
            }
        },
        RINSE{
            @Override
            void action() {
                Util.println("Rinsing");
            }
        },
        BLOWDRY{
            @Override
            void action() {
                Util.println("Blowing dry");
            }
        };
        abstract void action();
    }

    EnumSet<Cycle> mCycles = EnumSet.of(Cycle.BASIC, Cycle.RINSE);
    public void add(Cycle cycle){
        mCycles.add(cycle);
    }
    public void washCar(){
        for (Cycle cycle : mCycles) {
            cycle.action();
        }
    }

    @Override
    public String toString() {
        return mCycles.toString();
    }
    public static void main(String[] args){
        CarWash carWash = new CarWash();
        Util.println(carWash);
        carWash.washCar();
        // Order of addition is unimportant
        // 实际顺序和添加顺序无关，仍旧决定于 enum 的定义顺序
        carWash.add(Cycle.BLOWDRY);
        carWash.add(Cycle.BLOWDRY);
        carWash.add(Cycle.RINSE);
        carWash.add(Cycle.HOTWAX);
        Util.println(carWash);
        carWash.washCar();
    }
}
