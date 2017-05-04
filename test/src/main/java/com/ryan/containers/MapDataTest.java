package com.ryan.containers;

import com.ryan.array.CountingGenerator;
import com.ryan.generic.generator.Generator;
import com.ryan.array.RandomGenerator;
import com.ryan.util.Util;

import java.util.Iterator;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page462 Chapter 17.2.2 Map 生成器
 */

class Letters implements Generator<Pair<Integer, String>>, Iterable<Integer>{
    private int size = 9;
    private int number = 1;
    private char letter = 'A';
    @Override
    public Pair<Integer, String> next() {
        return new Pair<>(number++, "" + letter++);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return number < size;
            }

            @Override
            public Integer next() {
                return number++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

public class MapDataTest {
    public static void main(String[] args){
        // Pair Generator
        Util.println(MapData.map(new Letters(), 11));

        // Two separate Generators
        Util.println(MapData.map(new CountingGenerator.Character(), new RandomGenerator.String(3), 9));

        // A key Generator and a single value
        Util.println(MapData.map(new CountingGenerator.Character(), "Value", 6));

        // An Iterable and a value Generator
        Util.println(MapData.map(new Letters(), new RandomGenerator.String(3)));

        // An Iterable and a single value
        Util.println(MapData.map(new Letters(), "Pop"));
    }
}
