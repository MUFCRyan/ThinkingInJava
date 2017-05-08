package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page512 Chapter 17.11 实用方法：java.util.Collections 包含，其中的 min() 和 max() 只能用于 Collection
 * 对象、不能用于 List --> 无须担心Collection 是否已经被排序
 */

public class Utilities {
    static List<String> list = Arrays.asList("one Two three Four five six one".split(" "));
    public static void main(String[] args){
        Util.println(list);
        Util.println("'list' disjoint (Four)?: " + Collections.disjoint(list, Collections.singletonList("Four")));
        Util.println("max: " + Collections.max(list));
        Util.println("min: " + Collections.min(list));
        Util.println("max w/ comparator: " + Collections.max(list, String.CASE_INSENSITIVE_ORDER));
        Util.println("min w/ comparator: " + Collections.min(list, String.CASE_INSENSITIVE_ORDER));
        List<String> subList = Arrays.asList("Four five six".split(" "));
        Util.println("indexOfSubList: " + Collections.indexOfSubList(list, subList));
        Util.println("lastIndexOfSubList: " + Collections.lastIndexOfSubList(list, subList));
        Collections.replaceAll(list, "one", "Yo");
        Util.println("replaceAll: " + list);
        Collections.reverse(list);
        Util.println("reverse: " + list);
        Collections.rotate(list, 3);
        Util.println("rotate: " + list);
        List<String> source = Arrays.asList("in the matrix".split(" "));
        Collections.copy(list, source); // source 中的元素复制到 list 中
        Util.println("copy: " + list);
        Collections.swap(list, 0, list.size() - 1);
        Util.println("swap: " + list);
        Collections.shuffle(list, new Random(47));
        Util.println("shuffled: " + list);
        Collections.fill(list, "pop");
        Util.println("fill: " + list);
        Util.println("frequency of 'pop': " + Collections.frequency(list, "pop"));
        List<String> dups = Collections.nCopies(3, "snap");
        Util.println("sups: " + dups);
        Util.println("'list' disjoint 'dups'?: " + Collections.disjoint(list, dups));

        // Getting an old-style Enumeration
        Enumeration<String> enumeration = Collections.enumeration(dups);
        Vector<String> vector = new Vector<>();
        while (enumeration.hasMoreElements())
            vector.addElement(enumeration.nextElement());
        // Converting an old-style Vector to a List via an Enumeration
        ArrayList<String> arrayList = Collections.list(vector.elements());
        Util.println("arrayList: " + arrayList);
    }
}
