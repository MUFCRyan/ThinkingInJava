package com.ryan.containers;

import com.ryan.util.Util;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page469 Chapter 17.2.3 使用 Abstract 类
 */

public class CountingMapData extends AbstractMap<Integer, String>{
    private int size;
    private static String[] chars = ("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z").split(" ");
    public CountingMapData(int size){
        this.size = size < 0 ? 0 : size;
    }

    private static class Entry implements Map.Entry<Integer, String>{
        int index;
        Entry(int index){
            if (index < 0)
                this.index = index;
            else if (index > chars.length - 1)
                this.index = chars.length - 1;
            else
                this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            return Integer.valueOf(index).equals(o);
        }

        @Override
        public Integer getKey() {
            return index;
        }

        @Override
        public String getValue() {
            return chars[index & chars.length] + Integer.toString(index / chars.length);
        }

        @Override
        public String setValue(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(index).hashCode();
        }
    }

    /**
     * 此处因使用的是 LinkedHashSet 而非定制的 Set 类，故享元并未完全实现
     */
    @Override
    public Set<Map.Entry<Integer, String>> entrySet() {
        // LinkedHashSet retains initialization order
        Set<Map.Entry<Integer, String>> entries = new LinkedHashSet<Map.Entry<Integer, String>>();
        for (int i = 0; i < size; i++) {
            entries.add(new Entry(i));
        }
        return entries;
    }

    public static void main(String[] args){
        Util.println(new CountingMapData(60));
    }
}
