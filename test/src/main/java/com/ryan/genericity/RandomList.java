package com.ryan.genericity;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<T> {
    public static void main(String[] args){
        RandomList<String> rs = new RandomList<>();
        for (String s : ("The quick brown fox jumped over the lazy brown dog").split(" ")) {
            rs.add(s);
        }

        for (int i = 0; i < 11; i++) {
            Util.print(rs.select() + " ");
        }
    }

    private ArrayList<T> storage = new ArrayList<T>();
    private Random rand = new Random(47);
    private void add(T item){
        storage.add(item);
    }
    private T select(){
        return storage.get(rand.nextInt(storage.size()));
    }
}
