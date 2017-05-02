package com.ryan.generic.function_object_as_strategy;

import com.ryan.util.Util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page426 Chapter 15.18 函数对象用作策略
 * 策略设计模式：可以产生更加优雅的代码，因为它将“变化的事物”完全隔离到了一个函数对象中；
 * 函数对象就是在某种程度上行为像函数的对象 —— 一般会有一个相关方法；其价值在于它们可以传递出去且可以拥有在多个调用之间持久化的状态；
 * 函数对象主要由其目的区别，这里的目的就是要创建某种事物使其行为就像一个可以传递出去的单个方法一样 --> 这样就和策略模式紧耦合了，有时甚至无法区分
 */
// Different types of function objects
interface Combiner<T>{
    T combine(T x, T y);
}

interface UnaryFunction<R, T>{
    R function(T x);
}

interface Collector<T> extends UnaryFunction<T, T> {
    T result(); // Extract result of collecting parameter
}

interface UnaryPredicate<T> {
    boolean test(T x);
}

public class Functional {
    /**
     * Calls the Combiner object on each element to combine it with a running result, which is
     * finally returned
     */
    public static <T> T reduce(Iterable<T> seq, Combiner<T> combiner){
        Iterator<T> iterator = seq.iterator();
        if (iterator.hasNext()){
            T result = iterator.next();
            while (iterator.hasNext()){
                result = combiner.combine(result, iterator.next());
            }
            return result;
        }
        return null;
    }

    /**
     * Take a function object and call it on each object in the list, ignoring the return value.
     * The function object may act as a collecting parameter, so it is returned at the end
     */
    public static <T> Collector<T> forEach(Iterable<T> seq, Collector<T> function){
        for (T t : seq) {
            function.function(t);
        }
        return function;
    }

    /**
     * Creates a list of results by calling a function object for each object in the list
     */
    public static <R, T> List<R> transform(Iterable<T> seq, UnaryFunction<R, T> function){
        List<R> list = new ArrayList<>();
        for (T t : seq) {
            list.add(function.function(t));
        }
        return list;
    }

    /**
     * Applies a unary predicate to each item in a sequence, and returns a list of items that
     * produced "true"
     */
    public static <T> List<T> filter(Iterable<T> seq, UnaryPredicate<T> predicate){
        List<T> list = new ArrayList<>();
        for (T t : seq) {
            if (predicate.test(t))
                list.add(t);
        }
        return list;
    }

    /**
     * To use the above generic methods, we need to create function objects to adapt our
     * particular needs
     */
    static class IntegerAdder implements Combiner<Integer>{

        @Override
        public Integer combine(Integer x, Integer y) {
            return x + y;
        }
    }

    static class IntegerSubtracter implements Combiner<Integer>{

        @Override
        public Integer combine(Integer x, Integer y) {
            return x - y;
        }
    }

    static class BigDecimalAdder implements Combiner<BigDecimal>{

        @Override
        public BigDecimal combine(BigDecimal x, BigDecimal y) {
            return x.add(y);
        }
    }

    static class BigIntegerAdder implements Combiner<BigInteger>{

        @Override
        public BigInteger combine(BigInteger x, BigInteger y) {
            return x.add(y);
        }
    }

    static class AtomicLongAdder implements Combiner<AtomicLong>{
        @Override
        public AtomicLong combine(AtomicLong x, AtomicLong y) {
            // Not clear whether this is meaningful
            return new AtomicLong(x.addAndGet(y.get()));
        }
    }

    /**
     * We can even make a UnaryFunction with a "ulp" (Units in the last place):
     */
    static class BigDecimalUlp implements UnaryFunction<BigDecimal, BigDecimal>{

        @Override
        public BigDecimal function(BigDecimal x) {
            return x.ulp();
        }
    }

    static class GreaterThan <T extends Comparable<T>> implements UnaryPredicate<T>{
        private T bound;

        public GreaterThan(T x){
            bound = x;
        }

        @Override
        public boolean test(T x) {
            return x.compareTo(bound) > 0;
        }
    }

    static class MultiplyingIntegerCollector implements Collector<Integer>{
        private int value = 1;

        @Override
        public Integer function(Integer x) {
            return value *= x;
        }

        @Override
        public Integer result() {
            return value;
        }
    }

    public static void main(String[] args){
        // Generics, varargs & boxing working together
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Integer result = reduce(list, new IntegerAdder());
        Util.println(result);

        result = reduce(list, new IntegerSubtracter());
        Util.println(result);

        Util.println(filter(list, new GreaterThan<>(4)));

        Util.println(forEach(list, new MultiplyingIntegerCollector()).result());

        Util.println(forEach(filter(list, new GreaterThan<>(4)), new MultiplyingIntegerCollector()).result());

        MathContext mathContext = new MathContext(7);
        List<BigDecimal> bigDecimalList = Arrays.asList(
                new BigDecimal(1.1, mathContext),
                new BigDecimal(2.2, mathContext),
                new BigDecimal(3.3, mathContext),
                new BigDecimal(4.4, mathContext)
        );
        BigDecimal bigDecimalResult = reduce(bigDecimalList, new BigDecimalAdder());
        Util.println(bigDecimalResult);

        Util.println(filter(bigDecimalList, new GreaterThan<BigDecimal>(new BigDecimal(3))));

        // Use the prime-generation facility of BigInteger
        List<BigInteger> bigIntegerList = new ArrayList<>();
        BigInteger bigInteger = BigInteger.valueOf(11);
        for (int i = 0; i < 11; i++) {
            bigIntegerList.add(bigInteger);
            bigInteger = bigInteger.nextProbablePrime();
        }
        Util.println(bigIntegerList);

        BigInteger bigIntegerResult = reduce(bigIntegerList, new BigIntegerAdder());
        Util.println(bigIntegerResult);
        // The sum of this list of primes is also prime
        Util.println(bigIntegerResult.isProbablePrime(5));

        List<AtomicLong> atomicLongList = Arrays.asList(
                new AtomicLong(11),
                new AtomicLong(47),
                new AtomicLong(74),
                new AtomicLong(133)
        );
        AtomicLong atomicLongResult = reduce(atomicLongList, new AtomicLongAdder());
        Util.println(atomicLongResult);

        Util.println(transform(bigDecimalList, new BigDecimalUlp()));
    }
}
