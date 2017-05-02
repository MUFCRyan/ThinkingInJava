package com.ryan.generic.complements_lack_latent_type;

import com.ryan.util.Util;

import java.lang.reflect.Method;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page420 Chapter 15.17 对缺乏潜在类型机制的补偿
 * Book Page420 Chapter 15.17.1 反射
 */

class Mime{
    public void workAgainstTheWorld(){}
    public void sit(){
        Util.println("Pretending to sit!");
    }
    public void pushInvisibleWalls(){}

    @Override
    public String toString() {
        return "Mime";
    }
}

class SmartDog{
    public void speak(){
        Util.println("Woof!");
    }

    public void sit(){
        Util.println("Sitting!");
    }

    public void reproduce(){}
}

class CommunicationReflectively{
    public static void perform(Object speaker){
        Class<?> speakerClass = speaker.getClass();
        try {
            try {
                Method speak = speakerClass.getMethod("speak");
                speak.invoke(speaker); // 在反射中看起来像是方法.对象的调用形式
            } catch (NoSuchMethodException e) {
                Util.println(speaker + " cannot speak!");
            }

            try {
                Method sit = speakerClass.getMethod("sit");
                sit.invoke(speaker);
            } catch (NoSuchMethodException e){
                Util.println(speaker + " cannot sit!");
            }
        } catch (Exception e){
            throw new RuntimeException(speaker.toString(), e);
        }
    }
}

public class LatentReflection {
    public static void main(String[] args){
        Mime mime = new Mime();
        SmartDog smartDog = new SmartDog();
        CommunicationReflectively.perform(mime);
        CommunicationReflectively.perform(smartDog);
    }
}
