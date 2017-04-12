package com.ryan;

import com.ryan.genericity.generator.Generator;
import com.ryan.genericity.generator.Generators;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 *  Created by MUFCRyan on 2017/4/5.
 */

class BigFish {
    private static long counter = 0;
    private final long id = counter++;
    private BigFish(){}

    @Override
    public String toString() {
        return "BigFish " + id;
    }

    public static Generator<BigFish> generator(){
        return new Generator<BigFish>() {
            @Override
            public BigFish next() {
                return new BigFish();
            }
        };
    }
}

class LittleFish {
    private static long counter = 0;
    private final long id = counter++;
    private LittleFish(){}

    @Override
    public String toString() {
        return "LittleFish " + id;
    }

    public static Generator<LittleFish> generator(){
        return new Generator<LittleFish>() {
            @Override
            public LittleFish next() {
                return new LittleFish();
            }
        };
    }
}

class EatFish{
    public static void eat(BigFish bigFish, LittleFish littleFish){
        Util.println(bigFish + " ate " + littleFish);
    }

    public static void main(String[] args){
        Random random = new Random(47);

        Queue<LittleFish> foods = new LinkedList<>();
        Generators.fill(foods, LittleFish.generator(), 10);

        List<BigFish> bigFishes = new ArrayList<>();
        Generators.fill(bigFishes, BigFish.generator(), 3);

        for (LittleFish food : foods) {
            eat(bigFishes.get(random.nextInt(bigFishes.size())), food);
        }
    }
}
