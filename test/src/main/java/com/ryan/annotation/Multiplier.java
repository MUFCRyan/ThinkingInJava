package com.ryan.annotation;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page630 Chapter 20.3 使用 apt 处理注解
 */

@ExtractInterface("IMultiplier")
public class Multiplier {
    public int multiply(int x, int y){
        int total = 0;
        for (int i = 0; i < x; i++) {
            total = add(total, y);
        }
        return total;
    }

    private int add(int x, int y){
        return x + y;
    }

    public static void main(String[] args){
        Multiplier multiplier = new Multiplier();
        Util.println("7 * 14 = " + multiplier.multiply(7, 14));
    }
}
