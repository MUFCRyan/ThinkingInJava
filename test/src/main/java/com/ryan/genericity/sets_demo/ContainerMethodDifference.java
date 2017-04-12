package com.ryan.genericity.sets_demo;

import com.ryan.util.Sets;
import com.ryan.util.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 各容器之间不同的方法
 */

public class ContainerMethodDifference {
    static Set<String> methodSet(Class<?> type){
        TreeSet<String> result = new TreeSet<>();
        for (Method method : type.getMethods()) {
            result.add(method.getName());
        }
        return result;
    }

    static void interfaces(Class<?> type){
        Util.println("Interfaces in " + type.getSimpleName() + ": ");
        List<String> list = new ArrayList<>();
        for (Method method : type.getMethods()) {
            list.add(method.getName());
        }
        Util.println(list);
    }

    static Set<String> object = methodSet(Object.class);
    static {
        object.add("clone");
    }

    static void difference(Class<?> superSet, Class<?> subSet){
        Util.println(superSet.getSimpleName() + " extends " + subSet.getSimpleName() + ", add: ");
        Set<String> comp = Sets.difference(methodSet(superSet), methodSet(subSet));
        comp.removeAll(object);
        Util.println(comp);
        interfaces(superSet);
    }

    public static void main(String[] args){
        Util.println("Collections: " + methodSet(Collection.class));
        interfaces(Collection.class);
        difference(Set.class, Collection.class);
        difference(HashSet.class, Set.class);
        difference(LinkedHashSet.class, HashSet.class);
        difference(TreeSet.class, Set.class);
        difference(List.class, Collection.class);
        difference(ArrayList.class, List.class);
        difference(LinkedList.class, List.class);
        difference(Queue.class, Collection.class);
        difference(PriorityQueue.class, Queue.class);
        Util.println("Map: " + methodSet(Map.class));
        difference(HashMap.class, Map.class);
        difference(LinkedHashMap.class, Map.class);
        difference(SortedMap.class, Map.class);
        difference(TreeMap.class, Map.class);
    }
}
