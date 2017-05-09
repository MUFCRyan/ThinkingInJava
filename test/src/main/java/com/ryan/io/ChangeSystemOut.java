package com.ryan.io;

import java.io.PrintWriter;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page549 Chapter 18.8.2 将 System.out 转换为 PrintWriter，实质就是将 System.out 作为参数包裹到 PrintWriter
 * 中，最终输出还是在 System.out 指定的地方输出
 */

public class ChangeSystemOut {
    public static void main(String[] args){
        PrintWriter writer = new PrintWriter(System.out, true);
        writer.println("Hello, Ryan");
    }
}
