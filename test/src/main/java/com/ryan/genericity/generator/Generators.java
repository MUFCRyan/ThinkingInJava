package com.ryan.genericity.generator;

import com.ryan.genericity.coffee.Coffee;
import com.ryan.genericity.coffee.CoffeeGenerator;
import com.ryan.genericity.fibonacci.Fibonacci;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 用于Generator的泛型方法
 */

public class Generators {
    public static <T> Collection<T> fill(Collection<T> collection, Generator<T> generator, int
            count){
        for (int i = 0; i < count; i++) {
            collection.add(generator.next());
        }
        return collection;
    }

    public static void main(String[] args){
        Collection<Coffee> coffees = fill(new ArrayList<>(), new CoffeeGenerator(), 5);
        for (Coffee coffee : coffees) {
            Util.println(coffee.toString());
        }

        Collection<Integer> fibonacci = fill(new ArrayList<>(), new Fibonacci(), 10);
        for (Integer num : fibonacci) {
            Util.print(num + "  ");
        }
    }
}
