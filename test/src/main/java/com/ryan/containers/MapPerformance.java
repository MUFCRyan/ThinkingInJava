package com.ryan.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page508 Chapter 17.10.9 对 Map的选择
 *
 * HashMap 的性能因子：
 *      1. 容量：表中的桶位数
 *      2. 初始容量：表创建时所拥有的桶位数
 *      3. 尺寸：表中当前存储的项数
 *      4. 负载因子：尺寸/容量，默认为 0.75
 */

public class MapPerformance {
    static List<Test<Map<Integer, Integer>>> tests = new ArrayList<>();
    static {
        tests.add(new Test<Map<Integer, Integer>>("put") {
            @Override
            int test(Map<Integer, Integer> container, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    for (int j = 0; j < size; j++) {
                        container.put(j, j);
                    }
                }
                return loops * size;
            }
        });
    }

    static {
        tests.add(new Test<Map<Integer, Integer>>("get") {
            @Override
            int test(Map<Integer, Integer> container, TestParam tp) {
                int loops = tp.loops;
                int span = tp.size * 2;
                for (int i = 0; i < loops; i++) {
                    for (int j = 0; j < span; j++) {
                        container.get(j);
                    }
                }
                return loops * span;
            }
        });
    }

    static {
        tests.add(new Test<Map<Integer, Integer>>("iterate") {
            @Override
            int test(Map<Integer, Integer> container, TestParam tp) {
                int loops = tp.loops * 10;
                for (int i = 0; i < loops; i++) {
                    Iterator<Map.Entry<Integer, Integer>> iterator = container.entrySet().iterator();
                    while (iterator.hasNext())
                        iterator.next();
                }
                return loops * container.size();
            }
        });
    }

    public static void main(String[] args){
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);
        Tester.run(new TreeMap<>(), tests);
        Tester.run(new HashMap<>(), tests);
        Tester.run(new LinkedHashMap<>(), tests);
        Tester.run(new IdentityHashMap<>(), tests);
        Tester.run(new WeakHashMap<>(), tests);
        Tester.run(new Hashtable<>(), tests);
    }
}
