package com.ryan.io;

import com.ryan.util.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page525 Chapter 18 Java I/O 系统
 * 创建一个好的 I/O 系统很艰难的原因在于：要涵盖所有的可能性；不仅存在各种 I/O 源端和想要与之通信的接收端（文件、控制台、网络连接等）
 *      ，而且还需要以多种不同的方式与它们进行通信（顺序、随机存取、缓冲、二进制、按字符、按行、按字等）
 *
 * Book Page525 Chapter 18.1 File 类：是包含文件、文件夹、目录的总称，不仅是文件本身
 * Book Page525 Chapter 18.1.1 目录列表器
 */

public class DirList {
    public static void main(String[] args){
        File path = new File(".");
        String[] list;
        if (args.length == 0)
            list = path.list();
        else
            list = path.list(new DirFilter(args[0]));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String item : list) {
            Util.println(item);
        }
    }
}

/**
 * accept() 是策略模式的一个实例
 */
class DirFilter implements FilenameFilter{
    private Pattern mPattern;

    public DirFilter(String regex){
        mPattern = Pattern.compile(regex);
    }
    @Override
    public boolean accept(File file, String s) {
        return mPattern.matcher(s).matches();
    }
}

/**
 * 匿名内部类：DirList2、DirList3，优点在于将解决特定问题的代码隔离，聚拢于一点；缺点在于可读性不高
 */
class DirList2{
    public static FilenameFilter filter(final String regex){
        // Creation of anonymous inner class
        return new FilenameFilter() {
            private Pattern mPattern = Pattern.compile(regex);
            @Override
            public boolean accept(File file, String s) {
                return mPattern.matcher(s).matches();
            }
        };
        // End of anonymous inner class
    }

    public static void main(String[] args){
        File path = new File(".");
        String[] list;
        if (args.length == 0)
            list = path.list();
        else
            list = path.list(filter(args[0]));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String item : list) {
            Util.println(item);
        }
    }
}

class DirList3{
    public static void main(String[] args){
        File path = new File(".");
        String[] list;
        if (args.length == 0)
            list = path.list();
        else
            list = path.list(new FilenameFilter() {
                private Pattern mPattern = Pattern.compile(args[0]);
                @Override
                public boolean accept(File file, String s) {
                    return mPattern.matcher(s).matches();
                }
            });
        Arrays.sort(list);
        for (String item : list) {
            Util.println(item);
        }
    }
}
