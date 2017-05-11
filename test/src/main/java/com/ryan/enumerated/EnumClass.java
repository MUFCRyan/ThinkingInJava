package com.ryan.enumerated;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page590 Chapter 19 枚举类型
 * Book Page590 Chapter 19.1 基本 enum 特性
 */

enum Shrubbery {
    GROUND, CRAWLING, HANGING
}

public class EnumClass {
    public static void main(String[] args){
        for (Shrubbery value : Shrubbery.values()) {
            Util.println(value + " ordinal: " + value.ordinal());
            Util.print(value.compareTo(Shrubbery.CRAWLING) + "  ");
            Util.print(value.equals(Shrubbery.CRAWLING) + "  ");
            Util.println((value == Shrubbery.CRAWLING) + "  ");
            Util.println(value.getDeclaringClass());
            Util.println(value.name());
            Util.println("-----------------------------------------------------------");
        }

        for (String value : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrubbery = Enum.valueOf(Shrubbery.class, value);
            Util.println(shrubbery);
        }
    }
}
