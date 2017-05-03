package com.ryan.array;

import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page442 Chapter 16.6 创建测试数据
 * Book Page442 Chapter 16.6.2 数据生成器
 */

public class CountingGenerator {
    public static class Boolean implements Generator<java.lang.Boolean>{
        private boolean value = false;
        @Override
        public java.lang.Boolean next() {
            value = !value;
            return value;
        }
    }

    public static class Byte implements Generator<java.lang.Byte>{
        private byte value = 0;
        @Override
        public java.lang.Byte next() {
            value ++;
            return value;
        }
    }

    private static char[] chars = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();
    public static class Character implements Generator<java.lang.Character>{
        int index = -1;
        @Override
        public java.lang.Character next() {
            index = (index + 1) % chars.length;
            return chars[index];
        }
    }

    public static class String implements Generator<java.lang.String>{
        private int length = 7;
        Generator<java.lang.Character> characters = new Character();
        public String(){}
        public String(int length){
            this.length = length;
        }
        @Override
        public java.lang.String next() {
            char[] chars = new char[length];
            for (int i = 0; i < chars.length; i++) {
                chars[i] = characters.next();
            }
            return new java.lang.String(chars);
        }
    }

    public static class Short implements Generator<java.lang.Short>{
        private short value = 0;
        @Override
        public java.lang.Short next() {
            return value ++;
        }
    }

    public static class Integer implements Generator<java.lang.Integer>{
        private int value = 0;
        @Override
        public java.lang.Integer next() {
            return value ++;
        }
    }

    public static class Long implements Generator<java.lang.Long>{
        private long value = 0;
        @Override
        public java.lang.Long next() {
            return value ++;
        }
    }

    public static class Float implements Generator<java.lang.Float>{
        private float value = 0;
        @Override
        public java.lang.Float next() {
            float result = value;
            value += 1.0f;
            return result;
        }
    }

    public static class Double implements Generator<java.lang.Double>{
        private double value = 0.0;
        @Override
        public java.lang.Double next() {
            double result = value;
            value += 1.0;
            return result;
        }
    }
}

/**
 * 通过反射测试
 */
class GeneratorTest{
    public static int size = 10;
    public static void test(Class<?> surroundingClass){
        for (Class<?> type : surroundingClass.getClasses()) {
            Util.print(type.getSimpleName() + ": ");
            try {
                Generator<?> generator = (Generator<?>) type.newInstance();
                for (int i = 0; i < size; i++) {
                    Util.print(generator.next() + "  ");
                }
                Util.println();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args){
        test(CountingGenerator.class);
    }
}
