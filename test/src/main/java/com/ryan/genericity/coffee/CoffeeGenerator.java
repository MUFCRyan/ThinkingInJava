package com.ryan.genericity.coffee;

import com.ryan.genericity.generator.Generator;
import com.ryan.util.Util;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/4/5.
 */

public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {
    private static Class[] sTypes = new Class[]{Latte.class, Mocha.class, Capuccino.class, Ameicano
            .class, Breve.class};

    private static Random mRandom = new Random(47);

    private int mSize;

    public CoffeeGenerator() {}

    public CoffeeGenerator(int size) {
        this.mSize = size;
    }

    @Override
    public Coffee next() {
        try {
            return (Coffee) sTypes[mRandom.nextInt(sTypes.length)].newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    class CoffeeIterator implements Iterator<Coffee>{

        int count = mSize;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count --;
            return CoffeeGenerator.this.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
        CoffeeGenerator generator = new CoffeeGenerator();
        for (int i = 0; i < 5; i++) {
            Util.println(generator.next().toString());
        }
        for (Coffee c : new CoffeeGenerator(5)) {
            Util.println(c.toString());
        }
    }
}
