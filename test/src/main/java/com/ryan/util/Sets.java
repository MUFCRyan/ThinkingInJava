package com.ryan.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MUFCRyan on 2017/4/5.
 * Set实用的数学计算
 */

public class Sets {
    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    public static <T> Set<T> difference(Set<T> superSet, Set<T> subSet){
        Set<T> result = new HashSet<>(superSet);
        result.removeAll(subSet);
        return result;
    }

    // Reflexive -- everything is not in the intersection
    public static <T> Set<T> complement(Set<T> a, Set<T> b){
        return difference(union(a, b), intersection(a, b));
    }

}
