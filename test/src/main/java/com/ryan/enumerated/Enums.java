package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page596 Chapter 19.6 随机选取
 */

public class Enums {
    private static Random random = new Random(47);
    public static <T extends  Enum<T>> T random(Class<T> eClass){
        return random(eClass.getEnumConstants());
    }

    public static <T> T random(T[] values){
        return values[random.nextInt(values.length)];
    }
}

enum Activity{
    SITTING, LYING, STANDING, HOPPING, RUNNING, DODGING, JUMPING, FALLING, FLYING
}

class RandomTest{
    public static void main(String[] args){
        for (int i = 0; i < 20; i++) {
            Util.print(Enums.random(Activity.class) + " ");
        }
    }
}
