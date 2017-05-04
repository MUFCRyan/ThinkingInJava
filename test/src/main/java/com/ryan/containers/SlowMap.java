package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page490 Chapter 17.9.1 理解 hashCode()
 * 使用散列的目的在于：想要使用一个对象来查找另一个对象
 */

import com.ryan.util.Util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 本例使用一对 ArrayLists 实现了一个 Map，其中包含 Map 接口的完整实现，提供了 entrySet()
 * SlowMap 速度偏慢，问题在于对键的查询，键未按照任何特定顺序进行保存，只能进行速度最慢的线程查询
 */
public class SlowMap<K, V> extends AbstractMap<K, V>{
    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();
    public V put(K key, V value){
        V oldValue = get(key);
        if (!keys.contains(key)){
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    public V get(Object key){
        if (!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }

    /**
     * entrySet() 的恰当实现是在 Map 中提供视图而非副本，且该视图允许对原始映射表进行修改（副本不可修改）
     */
    public Set<Map.Entry<K, V>> entrySet(){
        Set<Map.Entry<K, V>> set = new HashSet<>();
        Iterator<K> kIterator = keys.iterator();
        Iterator<V> vIterator = values.iterator();
        while (kIterator.hasNext()){
            set.add(new MapEntry<K, V>(kIterator.next(), vIterator.next()));
        }
        return set;
    }

    public static void main(String[] args){
        SlowMap<String, String> map = new SlowMap<>();
        map.putAll(Countries.capitals(15));
        Util.println(map);
        Util.println(map.get("BULGARIA"));
        Util.println(map.entrySet());
    }
}
