package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/3.
 * key、value 都为 final 是为了使 Pair 成为只读的数据传输对象/信使
 */
public class Pair<K, V> {
    public final K key;
    public final V value;
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
