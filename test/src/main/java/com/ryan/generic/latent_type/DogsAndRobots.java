package com.ryan.generic.latent_type;

import com.ryan.typeinfo.pets.Dog;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page419 Chapter 15.16 潜在类型机制：一种代码组织和复用机制，只要求实现某个方法的子集而不是某个特定的类或接口，
 * 可横跨类继承结构、调用不属于某个公共接口的方法且不要求静态或动态类型检查，该机制是所有计算机编程的基本手段，不要求动态或静态类型检查；
 * 实例：Python（动态类型语言，所有类型检查发生在运行时）和C++（静态类型语言，所有检查发生在编译期）
 */

interface Performs{
    void speak();
    void sit();
}

class PerformingDog extends Dog implements Performs{

    @Override
    public void speak() {
        Util.println("Woof!");
    }

    @Override
    public void sit() {
        Util.println("Sitting!");
    }

    public void reproduce(){}
}

class Robot implements Performs{

    @Override
    public void speak() {
        Util.println("Click!");
    }

    @Override
    public void sit() {
        Util.println("Clank!");
    }

    public void oilChange(){}
}

class Communicate{
    public static <T extends Performs> void perform(T performer){
        performer.speak();
        performer.sit();
    }
}

public class DogsAndRobots {
    public static void main(String[] args){
        PerformingDog dog = new PerformingDog();
        Robot robot = new Robot();
        Communicate.perform(dog);
        Communicate.perform(robot);
    }
}
