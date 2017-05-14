package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/14.
 * Book Page613 Chapter 19.11 多路分发，好处在于方法调用时的优雅语法 --> 避免了在一个方法中判定多个对象的类型的丑陋代码；必须先明确此种做法确实有意义
 */

interface Item{
    Outcome complete(Item item); // 子类实现后此处是两路分发
    Outcome eval(Paper paper);
    Outcome eval(Scissors scissors);
    Outcome eval(Rock rock);
}

class Paper implements Item{
    @Override
    public Outcome complete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper paper) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Scissors scissors) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Rock rock) {
        return Outcome.WIN;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class Scissors implements Item{
    @Override
    public Outcome complete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper paper) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Scissors scissors) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Rock rock) {
        return Outcome.LOSE;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class Rock implements Item{
    @Override
    public Outcome complete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper paper) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Scissors scissors) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Rock rock) {
        return Outcome.DRAW;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

public class RoShamBo1 {
    static final int SIZE = 20;
    public static Random sRandom = new Random(47);
    public static Item newItem(){
        switch (sRandom.nextInt(3)){
            default:
            case 0: return new Scissors();
            case 1: return new Rock();
            case 2: return new Paper();
        }
    }

    public static void match(Item itemA, Item itemB){
        Util.println(itemA + " vs. " +itemB + ": " + itemA.complete(itemB));
    }

    public static void main(String[] args){
        for (int i = 0; i < SIZE; i++) {
            match(newItem(), newItem());
        }
    }
}
