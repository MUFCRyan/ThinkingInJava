package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page515 Chapter 17.11.2 设定只读的 Collection 和 Map
 */

public class ReadOnly {
    static Collection<String> data = new ArrayList<>(Countries.names());
    public static void main(String[] args){
        Collection<String> collection = Collections.unmodifiableCollection(new ArrayList<>(data));
        Util.println(collection);
        // collection.add("one");

        List<String> list = Collections.unmodifiableList(new ArrayList<>(data));
        ListIterator<String> listIterator = list.listIterator();
        Util.println(listIterator.next());
        // list.add("one");

        Set<String> set = Collections.unmodifiableSet(new HashSet<>(data));
        Util.println(set);
        // set.add("one");

        Set<String> sortedSet = Collections.unmodifiableSortedSet(new TreeSet<>(data));

        Map<String, String> map = Collections.unmodifiableMap(new HashMap<>(Countries.capitals(6)));
        Util.println(map);
        // map.put("Ralph", "Howdy!");

        Map<String, String> sortedMap = Collections.unmodifiableSortedMap(new TreeMap<>(Countries.capitals(6)));
    }
}
