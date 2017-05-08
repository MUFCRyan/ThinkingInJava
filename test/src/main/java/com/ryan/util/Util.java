package com.ryan.util;

public class Util {

    public static void println(){
        System.out.println();
    }

    public static void println(Object o){
        System.out.println(o);
    }

    public static void println(String s){
        System.out.println(s);
    }

    public static void print(String s){
        System.out.print(s);
    }

    public static void print(Object o){
        System.out.print(o);
    }

    public static void format(String s, Object o){System.out.format(s, o);}
}
