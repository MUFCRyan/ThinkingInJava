package com.ryan.containers;

import com.ryan.util.Util;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page492 Chapter 17.9.2 为速度而散列
 * 散列的价值在于速度 --> 使得查询得以快速进行：使用数组保存键的信息，通过键对象生成一个散列码作为数组的下标，不同的键可以产生相同的下标；数组保存值的 List
 * 查询过程：先计算散列码 --> 使用散列码查询数组 --> 跳转到特定位置再查询值 —— 这就是速度较快的原因
 */

public class SimpleHashMap<K, V> extends AbstractMap<K, V>{

    // Choose a prime number for the hash table size, to achieve a uniform distribution
    static final int SIZE = 512; // 散列桶的最理想的容量是2的整数次方，因该数可用掩码代替除法
    // You can't have a physical array of generics, but you can up-cast to one
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    public V put(K key, V value){
        V oldValue = null;
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            buckets[index] = new LinkedList<MapEntry<K, V>>();
        List<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
        ListIterator<MapEntry<K, V>> iterator = bucket.listIterator();
        boolean found = false;
        while (iterator.hasNext()){
            MapEntry<K, V> next = iterator.next();
            if (next.getKey().equals(key)){
                oldValue = next.getValue();
                iterator.set(pair); // Replace the old value
                found = true;
                break;
            }
        }
        if (!found){
            buckets[index].add(pair);
        }
        return oldValue;
    }

    public V get(Object key){
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            return null;
        for (MapEntry<K, V> pair : buckets[index]) {
            if (pair.getKey().equals(key))
                return pair.getValue();
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null)
                continue;
            for (MapEntry<K, V> pair : bucket) {
                set.add(pair);
            }
        }
        return set;
    }

    public static void main(String[] args){
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        map.putAll(Countries.capitals(20));
        Util.println(map);
        Util.println(map.get("ANGOLA"));
        Util.println(map.entrySet());
    }
}
