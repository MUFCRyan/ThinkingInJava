package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page514 Chapter 17.11.1 List 的排序和查询
 * 若使用 Comparator 进行排序，那么 binarySearch 必须使用相同的 Comparator
 */

public class ListSortSearch {
    public static void main(String[] args){
        List<String> list = new ArrayList<>(Utilities.list);
        list.addAll(Utilities.list);
        Util.println(list);

        Collections.shuffle(list, new Random(47));
        Util.println("shuffled: " + list);
        // Use a ListIterator to trim off the last element
        ListIterator<String> iterator = list.listIterator(10); // 10 为迭代起始位置，即 10 之前的元素不会被迭代到
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        Util.println("Trimmed: " + list);
        Collections.sort(list);
        Util.println("Sorted: " + list);
        String key = list.get(7);
        int index = Collections.binarySearch(list, key);
        Util.println("Location of " + key + " is " + index + ", list.get(" + index + ") = " + list.get(index));
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        Util.println("Case insensitive sorted: " + list);
        key = list.get(7);
        index = Collections.binarySearch(list, key, String.CASE_INSENSITIVE_ORDER);
        Util.println("Location of " + key + " is " + index + ", list.get(" + index + ") = " + list.get(index));
    }
}
