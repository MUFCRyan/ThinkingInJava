package com.ryan.containers;

import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page488 Chapter 17.9 散列和散列码
 */

public class Prediction {
    private static Random random = new Random(47);
    private boolean shadow = random.nextDouble() > 0.5;

    @Override
    public String toString() {
        if (shadow)
            return "Six more weeks of Winter\n";
        else
            return "Early Spring!\n";
    }
}
