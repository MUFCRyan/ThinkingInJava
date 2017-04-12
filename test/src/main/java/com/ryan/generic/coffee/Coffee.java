package com.ryan.generic.coffee;

/**
 * Created by MUFCRyan on 2017/4/5.
 */

public class Coffee {
    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "   " + id;
    }
}
