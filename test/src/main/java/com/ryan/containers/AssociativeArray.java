package com.ryan.containers;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page483 Chapter 17.8 理解 Map
 */

public class AssociativeArray<K, V> {
    private Object[][] pairs;
    private int index;
    public AssociativeArray(int length){
        pairs = new Object[length][2];
    }

    public void put(K key, V value){
        if (index >= pairs.length)
            throw new ArrayIndexOutOfBoundsException();
        pairs[index++] = new Object[]{key, value};
    }

    public V get(K key){
        for (int i = 0; i < index; i++) {
            if (key.equals(pairs[i][0]))
                return (V) pairs[i][1];
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < index; i++) {
            builder.append(pairs[i][0].toString());
            builder.append(" : ");
            builder.append(pairs[i][1].toString());
            if (i < index - 1)
                builder.append("\n");
        }
        return builder.toString();
    }

    public static void main(String[] args){
        AssociativeArray<String, String> map = new AssociativeArray<>(6);
        map.put("sky", "blue");
        map.put("grass", "green");
        map.put("ocean", "dancing");
        map.put("tree", "tall");
        map.put("earth", "brown");
        map.put("sun", "warm");
        try {
            map.put("extra", "Object");
        } catch (ArrayIndexOutOfBoundsException e){
            Util.println("Too many Objects!");
        }
        Util.println(map);
        Util.println(map.get("ocean"));
    }
}
