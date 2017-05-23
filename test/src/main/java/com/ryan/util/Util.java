package com.ryan.util;

import com.ryan.io.Directory;

import java.util.Locale;

public class Util {

    public static void println() {
        System.out.println();
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static void format(String s, Object o) {
        System.out.format(s, o);
    }

    public static void printf(String var1, Object... var2){
        System.out.printf(var1, var2);
    }

    public static void printf(Locale var1, String var2, Object... var3){
        System.out.printf(var1, var2, var3);
    }

    public static String getSpecifiedFilePath(String fileName) {
        Directory.TreeInfo treeInfo = Directory.walk(".", fileName);
        if (treeInfo.files.size() > 0) {
            if (treeInfo.files.size() == 1)
                return treeInfo.files.get(0).getPath();
            else
                return treeInfo.files.get(1).getPath();
        }
        return null;
    }
}
