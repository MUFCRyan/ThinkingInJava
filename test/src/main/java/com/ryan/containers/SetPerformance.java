package com.ryan.containers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page508 Chapter 17.10.4 对 Set 的选择
 */

public class SetPerformance {
    static List<Test<Set<Integer>>> tests = new ArrayList<>();
    static {
        tests.add(new Test<Set<Integer>>("add"){

            @Override
            int test(Set<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    for (int j = 0; j < size; j++) {
                        container.add(j);
                    }
                }
                return loops * size;
            }
        });
    }

    static {
        tests.add(new Test<Set<Integer>>("contains"){

            @Override
            int test(Set<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int span = tp.size * 2;
                for (int i = 0; i < loops; i++) {
                    for (int j = 0; j < span; j++) {
                        container.contains(j);
                    }
                }
                return loops * span;
            }
        });
    }

    static {
        tests.add(new Test<Set<Integer>>("iterate"){

            @Override
            int test(Set<Integer> container, TestParam tp) {
                int loops = tp.loops * 10;
                for (int i = 0; i < loops; i++) {
                    Iterator<Integer> iterator = container.iterator();
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
        Tester.fieldWidth = 10;
        Tester.run(new TreeSet<>(), tests);
        Tester.run(new HashSet<>(), tests);
        Tester.run(new LinkedHashSet<>(), tests);
    }
}
