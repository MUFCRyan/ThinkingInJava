package com.ryan.generic.exception;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/4/27.
 * Book Page409 Chapter 15.14 异常
 * 由于擦除导致泛型应用与异常十分受限：
 *      1. catch 语句不能捕获泛型类型的异常，因为在编译和运行时必须知道异常的确切类型
 *      2. 泛型类不能直接或间接继承自 Throwable --> 将进一步阻止定义不能捕获的泛型异常
 * 不过类型参数可能会在一个方法的 throws 子句中用到 --> 使得我们可以编写随检查型异常的类型而发生变化的泛型代码
 */

interface Processor<T, E extends Exception> {
    void process(List<T> resultCollector) throws E;
}

class ProcessRunner<T, E extends Exception> extends ArrayList<Processor<T, E>>{
    List<T> processAll() throws E{
        List<T> resultCollector = new ArrayList<>();
        for (Processor<T, E> processor : this) {
            processor.process(resultCollector);
        }
        return resultCollector;
    }
}

class Failure1 extends Exception{}

class Processor1 implements Processor<String, Failure1>{

    static int count = 3;
    @Override
    public void process(List<String> resultCollector) throws Failure1 {
        if (count-- > 1)
            resultCollector.add("Hep!");
        else
            resultCollector.add("Ho!");
        if (count < 0)
            throw new Failure1();
    }
}

class Failure2 extends Exception{}

class Processor2 implements Processor<Integer, Failure2>{
    static int count = 3;
    @Override
    public void process(List<Integer> resultCollector) throws Failure2 {
        if (count-- > 1)
            resultCollector.add(47);
        else
            resultCollector.add(11);
        if (count < 0)
            throw new Failure2();
    }
}

public class ThrowGenericException {
    public static void main(String[] args){
        ProcessRunner<String, Failure1> processor1 = new ProcessRunner<>();
        for (int i = 0; i < 3; i++) {
            processor1.add(new Processor1());
        }
        try {
            processor1.processAll();
        } catch (Failure1 failure1) {
            Util.println(failure1);
        }

        ProcessRunner<Integer, Failure2> processor2 = new ProcessRunner<>();
        for (int i = 0; i < 3; i++) {
            processor2.add(new Processor2());
        }
        try {
            processor2.processAll();
        } catch (Failure2 failure2) {
            Util.println(failure2);
        }
    }
}
