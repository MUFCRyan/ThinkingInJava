package com.ryan.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page516 Chapter 17.11.3 Collection 的同步控制
 *
 * 快速报错机制（fail-fast）：会探查容器上的任何除了自己的进程所进行的操作以外的所有变化，一旦发现其他进程修改了容器就会立刻抛出
 * ConcurrentModificationException
 */

public class Synchronization {
    public static void main(String[] args){
        Collection<String> collection = Collections.synchronizedCollection(new ArrayList<>());

        List<String> list = Collections.synchronizedList(new ArrayList<>());

        Set<String> set = Collections.synchronizedSet(new HashSet<>());

        Set<String> sortedSet = Collections.synchronizedSortedSet(new TreeSet<>());

        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());

        Map<String, String> sortedMap = Collections.synchronizedSortedMap(new TreeMap<>());
    }
}
