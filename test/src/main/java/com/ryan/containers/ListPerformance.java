package com.ryan.containers;

import com.ryan.net.mindview.util.CountingGenerator;
import com.ryan.net.mindview.util.Generated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/5.
 * Book Page502 Chapter 17.10.2 对List的选择 —— List性能测试
 */

public class ListPerformance {
    static Random random = new Random();
    static int reps = 1000;
    static List<Test<List<Integer>>> tests = new ArrayList<>();
    static List<Test<LinkedList<Integer>>> qTests = new ArrayList<>();
    static {
        tests.add(new Test<List<Integer>>("add"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    for (int j = 0; j < containerSize; j++) {
                        container.add(j);
                    }
                }
                return loops * containerSize;
            }
        });
    }

    static {
        tests.add(new Test<List<Integer>>("get"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.get(random.nextInt(containerSize));
                }
                return loops;
            }
        });
    }

    static {
        tests.add(new Test<List<Integer>>("set"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.set(random.nextInt(containerSize), 47);
                }
                return loops;
            }
        });
    }

    static {
        tests.add(new Test<List<Integer>>("iterate_add"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                final int LOOPS = 1000000;
                int half = container.size() / 2;
                // half表示迭代器的起始位置，此处即为从 List 中点开始
                ListIterator<Integer> iterator = container.listIterator(half);
                for (int i = 0; i < LOOPS; i++) {
                    iterator.add(47);
                }
                return LOOPS;
            }
        });
    }

    static {
        tests.add(new Test<List<Integer>>("insert"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                int loops = tp.loops;
                for (int i = 0; i < loops; i++) {
                    container.add(5, 47); // Minimize random-access cost
                }
                return loops;
            }
        });
    }

    static {
        tests.add(new Test<List<Integer>>("remove"){

            @Override
            int test(List<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    container.addAll(new CountingIntegerList(size));
                    while (container.size() > 5)
                        container.remove(5); // Minimize random-access cost
                }
                return loops * size;
            }
        });
    }

    // Tests for queue behavior
    static {
        qTests.add(new Test<LinkedList<Integer>>("addFirst"){

            @Override
            int test(LinkedList<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    for (int j = 0; j < containerSize; j++) {
                        container.addFirst(47);
                    }
                }
                return loops * containerSize;
            }
        });
    }

    static {
        qTests.add(new Test<LinkedList<Integer>>("addLast"){

            @Override
            int test(LinkedList<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    for (int j = 0; j < containerSize; j++) {
                        container.addLast(47);
                    }
                }
                return loops * containerSize;
            }
        });
    }

    static {
        qTests.add(new Test<LinkedList<Integer>>("rmFirst"){

            @Override
            int test(LinkedList<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    container.addAll(new CountingIntegerList(containerSize));
                    while (container.size() > 0) {
                        container.removeFirst();
                    }
                }
                return loops * containerSize;
            }
        });
    }

    static {
        qTests.add(new Test<LinkedList<Integer>>("rmLast"){

            @Override
            int test(LinkedList<Integer> container, TestParam tp) {
                int loops = tp.loops;
                int containerSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    container.clear();
                    container.addAll(new CountingIntegerList(containerSize));
                    while (container.size() > 0) {
                        container.removeLast();
                    }
                }
                return loops * containerSize;
            }
        });
    }

    static class ListTester extends Tester<List<Integer>>{

        public ListTester(List<Integer> container, List<Test<List<Integer>>> tests) {
            super(container, tests);
        }

        @Override
        protected List<Integer> initialize(int size) {
            container.clear();
            container.addAll(new CountingIntegerList(size));
            return container;
        }

        public static void run(List<Integer> list, List<Test<List<Integer>>> tests){
            new ListTester(list, tests).timedTest();
        }
    }

    public static void main(String[] args){
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);
        // Can only do these two tests on an array
        Tester<List<Integer>> arrayTest = new Tester<List<Integer>>(null, tests.subList(1, 3)){
            //This will be called before each test. It produces a non-resizeable array-backed list
            @Override
            protected List<Integer> initialize(int size) {
                Integer[] ia = Generated.array(Integer.class, new CountingGenerator.Integer(), size);
                return Arrays.asList(ia);
            }
        };
        arrayTest.setHeadLine("Array as List");
        arrayTest.timedTest();

        Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);
        ListTester.run(new ArrayList<>(), tests);
        ListTester.run(new LinkedList<>(), tests);
//        ListTester.run(new Vector<Integer>(), tests);
        Tester.fieldWidth = 12;
        Tester<LinkedList<Integer>> qTest = new Tester<>(new LinkedList<>(), qTests);
        qTest.setHeadLine("Queue tests");
        qTest.timedTest();
    }
}
