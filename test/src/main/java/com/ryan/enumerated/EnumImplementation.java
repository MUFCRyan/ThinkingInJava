package com.ryan.enumerated;

import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page596 Chapter 19.5 使用实现而非继承，此处是通过实现 Generator 完成的
 */

enum CartoonCharacter implements Generator<CartoonCharacter> {
    SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;
    private static Random random = new Random(47);
    @Override
    public CartoonCharacter next() {
        return values()[random.nextInt(values().length)];
    }
}

public class EnumImplementation {
    public static <T> void printNext(Generator<T> generator){
        Util.println(generator.next() + ", ");
    }

    public static void main(String[] args){
        CartoonCharacter slappy = CartoonCharacter.SLAPPY;
        for (int i = 0; i < 10; i++) {
            printNext(slappy);
        }
    }
}
