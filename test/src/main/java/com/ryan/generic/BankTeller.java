package com.ryan.generic;

import com.ryan.generic.generator.Generator;
import com.ryan.generic.generator.Generators;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 泛型在匿名内部类中的使用，Customer和Teller只有private构造器，外部只能通过generator()对其进行访问，二者的generator()
 * 都是static的，所以无法作为接口的一部分，但是可以作为抽象类的一部分
 */

class Customer {
    private static long counter = 0;
    private final long id = counter++;

    private Customer() {
    }

    @Override
    public String toString() {
        return "Customer " + id;
    }

    // A method to produce Generator objects
    public static Generator<Customer> generator() {
        return new Generator<Customer>() {
            @Override
            public Customer next() {
                return new Customer();
            }
        };
    }
}

class Teller {
    private static long counter = 0;
    private final long id = counter++;

    private Teller() {
    }

    @Override
    public String toString() {
        return "Teller " + id;
    }

    public static Generator<Teller> generator() {
        return new Generator<Teller>() {
            @Override
            public Teller next() {
                return new Teller();
            }
        };
    }
}

class BankTeller {
    public static void serve(Teller teller, Customer customer){
        Util.println(teller + " serve " + customer);
    }

    public static void main(String[] args){
        Random random = new Random(47);
        Queue<Customer> line = new LinkedList<>();
        Generators.fill(line, Customer.generator(), 15);

        List<Teller> tellers = new ArrayList<>();
        Generators.fill(tellers, Teller.generator(), 6);

        for (Customer customer : line) {
            serve(tellers.get(random.nextInt(tellers.size())), customer);
        }
    }
}
