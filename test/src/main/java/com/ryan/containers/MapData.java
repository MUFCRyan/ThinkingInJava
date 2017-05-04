package com.ryan.containers;

import com.ryan.generic.generator.Generator;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page462 Chapter 17.2.2 Map 生成器
 */

public class MapData<K, V> extends LinkedHashMap<K, V> {
    // A single Pair Generator
    public MapData(Generator<Pair<K, V>> generator, int quantity){
        for (int i = 0; i < quantity; i++) {
            Pair<K, V> pair = generator.next();
            put(pair.key, pair.value);
        }
    }

    // Two separate Generators
    public MapData(Generator<K> kGenerator, Generator<V> vGenerator, int quantity){
        for (int i = 0; i < quantity; i++) {
            put(kGenerator.next(), vGenerator.next());
        }
    }

    // A key generator and a single value
    public MapData(Generator<K> kGenerator, V value, int quantity){
        for (int i = 0; i < quantity; i++) {
            put(kGenerator.next(), value);
        }
    }

    // An Iterable and a value Generator
    public MapData(Iterable<K> kIterable, Generator<V> vGenerator){
        Iterator<K> iterator = kIterable.iterator();
        while (iterator.hasNext()){
            put(iterator.next(), vGenerator.next());
        }
    }

    // An Iterable and a single value
    public MapData(Iterable<K> kIterable, V value){
        Iterator<K> iterator = kIterable.iterator();
        while (iterator.hasNext()){
            put(iterator.next(), value);
        }
    }

    // Generic convenience methods
    public static  <K, V> MapData<K, V> map(Generator<Pair<K, V>> generator, int quantity){
        return new MapData<K, V>(generator, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> kGenerator, Generator<V> vGenerator, int quantity){
        return new MapData<K, V>(kGenerator, vGenerator, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> kGenerator, V value, int quantity){
        return new MapData<K, V>(kGenerator, value, quantity);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> kIterable, Generator<V> vGenerator){
        return new MapData<K, V>(kIterable, vGenerator);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> kIterable, V value){
        return new MapData<K, V>(kIterable, value);
    }
}
