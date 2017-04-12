package com.ryan.generic.generator;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/5.
 * BasicGenerator的Demo
 */

public class BasicGeneratorDemo {
    public static void main(String[] args){
        Generator<CountedObject> generator = BasicGenerator.create(CountedObject.class);
        for (int i = 0; i < 5; i++) {
            Util.println(generator.next().toString());
        }
    }
}
