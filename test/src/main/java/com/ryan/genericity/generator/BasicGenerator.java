package com.ryan.genericity.generator;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 通用的Generator，用于生成某各类的对象，该类必须具备两个特点：1.public类；2.具备默认的构造器
 */

public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            // Assume type is a public class
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Product a default generator given type token:
    public static <T> Generator<T> create(Class<T> type){
        return new BasicGenerator<>(type);
    }
}
