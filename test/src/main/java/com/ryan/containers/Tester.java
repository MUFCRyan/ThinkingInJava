package com.ryan.containers;

import com.ryan.util.Util;

import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/5.
 * Book Page500 Chapter 17.10.1 性能测试框架
 */

public class Tester<C> {
    public static int fieldWidth = 8;
    public static TestParam[] defaultParams = TestParam.array(new int[]{
            10, 5000, 100, 5000, 1000, 5000, 10000, 500
    });
    protected C container;
    protected C initialize(int size){
        return container;
    }
    private String headLine = "";
    private List<Test<C>> tests;
    private String stringFiled(){
        return "%" + fieldWidth + "s";
    }
    private String numberField(){
        return "%" + fieldWidth + "d";
    }
    private static int sizeWidth = 5;
    private static String sizeField = "%" + sizeWidth + "s";
    private TestParam[] paramList = defaultParams;
    public Tester(C container, List<Test<C>> tests){
        this.container = container;
        this.tests = tests;
        if (container != null)
            headLine = container.getClass().getSimpleName();
    }
    public Tester(C container, List<Test<C>> tests, TestParam[] paramList){
        this(container, tests);
        this.paramList = paramList;
    }
    public void setHeadLine(String newHeadLine){
        this.headLine = newHeadLine;
    }
    // Generic methods for Convenience
    public static <C> void run(C container, List<Test<C>> tests){
        new Tester<C>(container, tests).timedTest();
    }
    public static <C> void run(C container, List<Test<C>> tests, TestParam[] paramList){
        new Tester<C>(container, tests, paramList).timedTest();
    }
    private void disPlayHeader(){
        // Calculate width and pad with '-'
        int width = fieldWidth * tests.size() + sizeWidth;
        int dashLength = width - headLine.length() - 1;
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < dashLength / 2; i++) {
            header.append('-');
        }
        header.append(' ');
        header.append(headLine);
        header.append(' ');
        for (int i = 0; i < dashLength / 2; i++) {
            header.append('-');
        }
        Util.println(header);
        // Print column headers
        Util.format(sizeField, "size");
        for (Test<C> test : tests) {
            Util.format(stringFiled(), test.name);
        }
        Util.println();
    }
    // Run the tests for this container
    public void timedTest(){
        disPlayHeader();
        for (TestParam param : paramList) {
            Util.format(sizeField, param.size);
            for (Test<C> test : tests) {
                C container = initialize(param.size);
                long start = System.nanoTime();
                int reps = test.test(container, param);
                long duration = System.nanoTime() - start;
                long timePerRep = duration / reps; // Nano seconds
                Util.format(numberField(), timePerRep);
            }
            Util.println();
        }
    }
}
